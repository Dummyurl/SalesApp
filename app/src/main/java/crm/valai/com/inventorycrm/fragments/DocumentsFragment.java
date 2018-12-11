package crm.valai.com.inventorycrm.fragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nbsp.materialfilepicker.MaterialFilePicker;
import com.nbsp.materialfilepicker.ui.FilePickerActivity;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import crm.valai.com.inventorycrm.R;
import crm.valai.com.inventorycrm.interfaces.FragmentListner;
import crm.valai.com.inventorycrm.modals.GetCustomerDetailsPOJO;
import crm.valai.com.inventorycrm.modals.GetCustomerDocumentPath;
import crm.valai.com.inventorycrm.modals.InsertUpdateItemPOJO;
import crm.valai.com.inventorycrm.modals.LogInPOJO;
import crm.valai.com.inventorycrm.network.ApiClient;
import crm.valai.com.inventorycrm.network.JsonClient;
import crm.valai.com.inventorycrm.network.RestClient;
import crm.valai.com.inventorycrm.utils.AlbumStorageDirFactory;
import crm.valai.com.inventorycrm.utils.BaseAlbumDirFactory;
import crm.valai.com.inventorycrm.utils.FroyoAlbumDirFactory;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;
import static crm.valai.com.inventorycrm.fragments.AdditionalDetailsFragment.viewPagerChangeInterface;
import static crm.valai.com.inventorycrm.utils.AppConstants.API_RESPONSE_CODE_PUNCH_IN_OUT;
import static crm.valai.com.inventorycrm.utils.AppConstants.API_RESPONSE_CODE_UPLOAD;
import static crm.valai.com.inventorycrm.utils.AppConstants.GET_CUSTOMER_DOCUMENT_PATH;
import static crm.valai.com.inventorycrm.utils.AppConstants.REQUEST_CAMERA;
import static crm.valai.com.inventorycrm.utils.AppConstants.REQUEST_CHOOSER;
import static crm.valai.com.inventorycrm.utils.AppConstants.REQUEST_DOC_FILE;
import static crm.valai.com.inventorycrm.utils.AppConstants.REQUEST_PERMISSION_CODE_CAMERA;
import static crm.valai.com.inventorycrm.utils.AppConstants.REQUEST_PERMISSION_CODE_STORAGE;
import static crm.valai.com.inventorycrm.utils.AppConstants.REQUEST_PERMISSION_CODE_STORAGE_DOCUMENTS;
import static crm.valai.com.inventorycrm.utils.AppConstants.RESPONSE_CODE;
import static crm.valai.com.inventorycrm.utils.AppConstants.TRUE;
import static crm.valai.com.inventorycrm.utils.CommonUtils.prepareFilePart;
import static crm.valai.com.inventorycrm.utils.CommonUtils.prepareStringPart;

public class DocumentsFragment extends BaseFragment {
    public static final String TAG = DocumentsFragment.class.getSimpleName();

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private FragmentListner fragmentListner;
    private OnFragmentInteractionListener mListener;
    private String imagePath = "";
    private File outputFile1, outputFile2, outputFile3, outputFile4, outputFile5;
    private AlbumStorageDirFactory mAlbumStorageDirFactory = null;
    private String tag;
    private List<LogInPOJO.Datum> logInResList;
    private Integer customerId;
    private List<GetCustomerDetailsPOJO.Datum> listCustomerDetails;
    private InsertUpdateItemPOJO insertUpdateItemPOJO;

    @BindView(R.id.tvIDCardDocument)
    TextView tvIDCardDocument;

    @BindView(R.id.tvGSTCertificate)
    TextView tvGSTCertificate;

    @BindView(R.id.tvAddressProof)
    TextView tvAddressProof;

    @BindView(R.id.tvVisitingCardFront)
    TextView tvVisitingCardFront;

    @BindView(R.id.tvVisitingCardBack)
    TextView tvVisitingCardBack;

    public DocumentsFragment() {
        // Required empty public constructor
    }

    public static DocumentsFragment newInstance(String param1, String param2) {
        DocumentsFragment fragment = new DocumentsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @SuppressLint("ObsoleteSdkInt")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO) {
            mAlbumStorageDirFactory = new FroyoAlbumDirFactory();
        } else {
            mAlbumStorageDirFactory = new BaseAlbumDirFactory();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.document_customer_fragment, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @SuppressLint("UseSparseArrays")
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        customerId = fragmentListner.getAppPreferenceHelper().getCustomerId();
        logInResList = new ArrayList<>();
        logInResList = fragmentListner.getSignInResultList();

        listCustomerDetails = new ArrayList<>();
        HashMap<Integer, List<GetCustomerDetailsPOJO.Datum>> hashCustomerDetailsList = new HashMap<>();
        hashCustomerDetailsList = fragmentListner.getCustomerDetailsList();
        if (hashCustomerDetailsList != null && hashCustomerDetailsList.size() > 0
                && hashCustomerDetailsList.containsKey(customerId)) {
            listCustomerDetails = hashCustomerDetailsList.get(customerId);
        }

        if (logInResList != null && logInResList.size() > 0) {
            getCustomerDocumentPath();
            getCustomerDetails(customerId);
        }
        outputFile1 = new File("");
        outputFile2 = new File("");
        outputFile3 = new File("");
        outputFile4 = new File("");
        outputFile5 = new File("");
        setDocuments();
    }

    private void setDocuments() {
        if (listCustomerDetails != null && listCustomerDetails.size() > 0) {
            if (listCustomerDetails.get(0).getVisiting_card() != null && !listCustomerDetails.get(0).getVisiting_card().equals("")) {
                tvVisitingCardFront.setText(listCustomerDetails.get(0).getVisiting_card());
            } else {
                tvVisitingCardFront.setText(null);
            }

            if (listCustomerDetails.get(0).getVisiting_card_back() != null && !listCustomerDetails.get(0).getVisiting_card_back().equals("")) {
                tvVisitingCardBack.setText(listCustomerDetails.get(0).getVisiting_card_back());
            } else {
                tvVisitingCardBack.setText(null);
            }

            if (listCustomerDetails.get(0).getId_Card_Proof() != null && !listCustomerDetails.get(0).getId_Card_Proof().equals("")) {
                tvIDCardDocument.setText(listCustomerDetails.get(0).getId_Card_Proof());
            } else {
                tvIDCardDocument.setText(null);
            }

            if (listCustomerDetails.get(0).getAddress_Proof() != null && !listCustomerDetails.get(0).getAddress_Proof().equals("")) {
                tvAddressProof.setText(listCustomerDetails.get(0).getAddress_Proof());
            } else {
                tvAddressProof.setText(null);
            }

            if (listCustomerDetails.get(0).getGst_Certificate() != null && !listCustomerDetails.get(0).getGst_Certificate().equals("")) {
                tvGSTCertificate.setText(listCustomerDetails.get(0).getGst_Certificate());
            } else {
                tvGSTCertificate.setText(null);
            }
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            fragmentListner = (FragmentListner) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement MyInterface ");
        }
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    @OnClick(R.id.btnBack)
    public void onBackClick() {
        viewPagerChangeInterface.onLeftMoveClick();
    }

    @OnClick(R.id.btnSave)
    public void btnSaveClick() {
        if (!outputFile1.exists() && !outputFile2.exists()
                && !outputFile3.exists() && !outputFile4.exists()
                && !outputFile5.exists()) {
            showMessage(getString(R.string.select_documents));
            return;
        }
        showLoading();
        checkFiles();
    }

    @OnClick(R.id.tvIDCardDocumentBrowse)
    public void onCardDocumentButtonClick() {
        hideKeyboard();
        tag = "IDCardDocument";
        selectImageDialog();
    }

    @OnClick(R.id.tvGSTCertificateBrowse)
    public void onGSTCertificateButtonClick() {
        hideKeyboard();
        tag = "GSTCertificate";
        selectImageDialog();
    }

    @OnClick(R.id.tvAddressProofBrowse)
    public void onAddressProofButtonClick() {
        hideKeyboard();
        tag = "AddressProof";
        selectImageDialog();
    }

    @OnClick(R.id.tvVisitingCardFrontBrowse)
    public void onVisitingCardFrontButtonClick() {
        hideKeyboard();
        tag = "VisitingCardFront";
        selectImageDialog();
    }

    @OnClick(R.id.tvVisitingCardBackBrowse)
    public void onVisitingCardBackButtonClick() {
        hideKeyboard();
        tag = "VisitingCardBack";
        selectImageDialog();
    }

    private void selectImageDialog() {
        pickFromFile();
    }

    private void pickFromFile() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN
                && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermission(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_PERMISSION_CODE_STORAGE_DOCUMENTS);
        } else {
            new MaterialFilePicker()
                    .withSupportFragment(DocumentsFragment.this)
                    .withRequestCode(REQUEST_DOC_FILE)
                    .withHiddenFiles(true)
                    .withTitle(getString(R.string.app_name))
                    .start();
        }
    }

    private void pickFromGallery() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN
                && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermission(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_PERMISSION_CODE_STORAGE);
        } else {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            startActivityForResult(Intent.createChooser(intent, getString(R.string.label_select_picture)), REQUEST_CHOOSER);
        }
    }

    public void cameraIntent() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN
                && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermission(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_PERMISSION_CODE_CAMERA);
        } else {
            if (!getContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
                showMessage(getString(R.string.camera_not_support));
                return;
            }

            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            File file = null;
            try {
                file = new File(setUpPhotoFile().getPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
            Uri photoUri = FileProvider.getUriForFile(getContext(), getContext().getPackageName() + ".provider", file);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            if (intent.resolveActivity(getContext().getPackageManager()) != null) {
                startActivityForResult(intent, REQUEST_CAMERA);
            }
        }
    }

    private File setUpPhotoFile() throws IOException {
        File f = createImageFile();
        imagePath = f.getAbsolutePath();
        return f;
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        @SuppressLint("SimpleDateFormat") String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String JPEG_FILE_PREFIX = "IMG_";
        String imageFileName = JPEG_FILE_PREFIX + timeStamp + "_";
        File albumF = getAlbumDir();
        String JPEG_FILE_SUFFIX = ".jpg";
        return File.createTempFile(imageFileName, JPEG_FILE_SUFFIX, albumF);
    }

    @Nullable
    private File getAlbumDir() {
        File storageDir = null;

        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {

            storageDir = mAlbumStorageDirFactory.getAlbumStorageDir(getAlbumName());

            if (storageDir != null) {
                if (!storageDir.mkdirs()) {
                    if (!storageDir.exists()) {
                        Log.d("CameraSample", "failed to create directory");
                        return null;
                    }
                }
            }

        } else {
            showMessage("External storage is not mounted READ/WRITE.");
            Log.v(getString(R.string.app_name), "External storage is not mounted READ/WRITE.");
        }

        return storageDir;
    }

    public String getAlbumName() {
        return getString(R.string.app_name);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.e("onActivityResult", TAG);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_DOC_FILE:
                    imagePath = data.getStringExtra(FilePickerActivity.RESULT_FILE_PATH);
                    if (imagePath != null) {
                        switch (tag) {
                            case "IDCardDocument":
                                outputFile1 = new File(imagePath);
                                String fileName = imagePath.substring(imagePath.lastIndexOf("/") + 1);
                                tvIDCardDocument.setText(null);
                                tvIDCardDocument.setText(fileName);
                                break;
                            case "GSTCertificate":
                                outputFile2 = new File(imagePath);
                                String fileName2 = imagePath.substring(imagePath.lastIndexOf("/") + 1);
                                tvGSTCertificate.setText(null);
                                tvGSTCertificate.setText(fileName2);
                                break;
                            case "AddressProof":
                                outputFile3 = new File(imagePath);
                                String fileName3 = imagePath.substring(imagePath.lastIndexOf("/") + 1);
                                tvAddressProof.setText(null);
                                tvAddressProof.setText(fileName3);
                                break;
                            case "VisitingCardFront":
                                outputFile4 = new File(imagePath);
                                String fileName4 = imagePath.substring(imagePath.lastIndexOf("/") + 1);
                                tvVisitingCardFront.setText(null);
                                tvVisitingCardFront.setText(fileName4);
                                break;
                            case "VisitingCardBack":
                                outputFile5 = new File(imagePath);
                                String fileName5 = imagePath.substring(imagePath.lastIndexOf("/") + 1);
                                tvVisitingCardBack.setText(null);
                                tvVisitingCardBack.setText(fileName5);
                                break;
                        }
                    }
                    break;
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_PERMISSION_CODE_STORAGE: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    pickFromGallery();
                } else {
                    Log.e(TAG, "Storage Permission Not Granted");
                    showMessage(getString(R.string.allow_storage_permission));
                }

                break;
            }

            case REQUEST_PERMISSION_CODE_CAMERA: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    cameraIntent();
                } else {
                    Log.e(TAG, "Storage Permission Not Granted");
                    showMessage(getString(R.string.allow_storage_permission));
                }

                break;
            }

            case REQUEST_PERMISSION_CODE_STORAGE_DOCUMENTS: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    pickFromFile();
                } else {
                    Log.e(TAG, "Storage Permission Not Granted");
                    showMessage(getString(R.string.allow_storage_permission));
                }

                break;
            }
        }
    }

    private void checkFiles() {
        if (outputFile1 != null && outputFile1.exists()) {
            Log.e("outputFile1", "outputFile1");
            uploadDocuments(outputFile1, "idCard");
            return;
        }

        if (outputFile2 != null && outputFile2.exists()) {
            Log.e("outputFile2", "outputFile2");
            uploadDocuments(outputFile2, "gst");
            return;
        }

        if (outputFile3 != null && outputFile3.exists()) {
            Log.e("outputFile3", "outputFile3");
            uploadDocuments(outputFile3, "address");
            return;
        }

        if (outputFile4 != null && outputFile4.exists()) {
            Log.e("outputFile4", "outputFile4");
            uploadDocuments(outputFile4, "visitingcard");
            return;
        }

        if (outputFile5 != null && outputFile5.exists()) {
            Log.e("outputFile5", "outputFile5");
            uploadDocuments(outputFile5, "visitingcardback");
        }

        emptyTextFields();
        hideLoading();
        if (insertUpdateItemPOJO != null) {
            showMessage(insertUpdateItemPOJO.getResponseMessage());
        }
    }

    private void uploadDocuments(final File outPutFilePath, String documentType) {

        hideKeyboard();

        ApiClient apiClient = new ApiClient();
        RestClient restClientAPI = apiClient.getClient();

        MultipartBody.Part fileBody1 = prepareFilePart("file", outPutFilePath);
        MultipartBody.Part fileBody2 = prepareStringPart("compId", String.valueOf(logInResList.get(0).getCompId()));
        MultipartBody.Part fileBody3 = prepareStringPart("customerId", String.valueOf(customerId));
        MultipartBody.Part fileBody4 = prepareStringPart("loginid", String.valueOf(logInResList.get(0).getLoginId()));
        MultipartBody.Part fileBody5 = prepareStringPart("token", String.valueOf(logInResList.get(0).getToken()));
        MultipartBody.Part fileBody6 = prepareStringPart("documentType", documentType);

        Call<InsertUpdateItemPOJO> call = restClientAPI.uploadDocuments(fileBody1, String.valueOf(logInResList.get(0).getCompId()),
                String.valueOf(customerId), String.valueOf(logInResList.get(0).getLoginId()), String.valueOf(logInResList.get(0).getToken()),
                documentType);

        call.enqueue(new retrofit2.Callback<InsertUpdateItemPOJO>() {
            @Override
            public void onResponse(@NonNull Call<InsertUpdateItemPOJO> call, @NonNull Response<InsertUpdateItemPOJO> response) {
                insertUpdateItemPOJO = response.body();
                int code = response.code();
                Log.e(TAG, "code>>>>" + code);
                if (code == RESPONSE_CODE) {
                    if (insertUpdateItemPOJO != null) {
                        if (insertUpdateItemPOJO.getResponseCode().equals(API_RESPONSE_CODE_PUNCH_IN_OUT) ||
                                insertUpdateItemPOJO.getResponseCode().equals(API_RESPONSE_CODE_UPLOAD)) {
                            if (insertUpdateItemPOJO.getResponseStatus().equals(TRUE)) {
                                if (outPutFilePath.equals(outputFile1)) {
                                    outputFile1 = new File("");
                                    checkFiles();
                                    return;
                                }
                                if (outPutFilePath.equals(outputFile2)) {
                                    outputFile2 = new File("");
                                    checkFiles();
                                    return;
                                }
                                if (outPutFilePath.equals(outputFile3)) {
                                    outputFile3 = new File("");
                                    checkFiles();
                                    return;
                                }
                                if (outPutFilePath.equals(outputFile4)) {
                                    outputFile4 = new File("");
                                    checkFiles();
                                    return;
                                }
                                if (outPutFilePath.equals(outputFile5)) {
                                    outputFile5 = new File("");
                                }
                                emptyTextFields();
                                hideLoading();
                                showMessage(insertUpdateItemPOJO.getResponseMessage());
                            } else {
                                hideLoading();
                                showMessage(insertUpdateItemPOJO.getResponseMessage());
                            }
                        } else {
                            hideLoading();
                        }
                    } else {
                        hideLoading();
                    }
                } else {
                    hideLoading();
                }
            }

            @Override
            public void onFailure(@NonNull Call<InsertUpdateItemPOJO> call, @NonNull Throwable t) {
                Log.e(TAG, "Throwable>>>>" + t.getMessage());
                hideLoading();
                showMessage(t.getMessage());
            }
        });
    }

    private void emptyTextFields() {
        tvAddressProof.setText(null);
        tvGSTCertificate.setText(null);
        tvIDCardDocument.setText(null);
        tvVisitingCardBack.setText(null);
        tvVisitingCardFront.setText(null);
    }

    private void getCustomerDocumentPath() {
        hideKeyboard();

        ApiClient apiClient = new ApiClient();
        RestClient restClientAPI = apiClient.getClient();

        JsonClient jsonClient = new JsonClient();
        JSONObject jsonObject = jsonClient.getPurposeOfVisitJson(logInResList.get(0).getLoginId(),
                logInResList.get(0).getToken(), GET_CUSTOMER_DOCUMENT_PATH);

        RequestBody body = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());

        Call<GetCustomerDocumentPath> call = restClientAPI.getCustomerDocumentPath(body);
        call.enqueue(new retrofit2.Callback<GetCustomerDocumentPath>() {
            @Override
            public void onResponse(@NonNull Call<GetCustomerDocumentPath> call, @NonNull Response<GetCustomerDocumentPath> response) {
                GetCustomerDocumentPath insertUpdateItemPOJO = response.body();
                int code = response.code();
                Log.e(TAG, "code>>>>" + code);
                if (code == RESPONSE_CODE) {
                    if (insertUpdateItemPOJO != null) {
                        if (insertUpdateItemPOJO.getResponseCode().equals(API_RESPONSE_CODE_PUNCH_IN_OUT)) {
                            if (insertUpdateItemPOJO.getResponseStatus().equals(TRUE)) {
                                //showMessage(insertUpdateItemPOJO.getResponseMessage());
                                for (int i = 0; i < insertUpdateItemPOJO.getData().size(); i++) {
                                    Log.e("PAth", "PAth>>" + insertUpdateItemPOJO.getData().get(i).getName());
                                }

                            } else {
                                showMessage(insertUpdateItemPOJO.getResponseMessage());
                            }
                        }
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<GetCustomerDocumentPath> call, @NonNull Throwable t) {
                Log.e(TAG, "Throwable>>>>" + t.getMessage());
                showMessage(getString(R.string.internet_not_available));
            }
        });
    }

    private void getCustomerDetails(final Integer customerId) {

        hideKeyboard();

        ApiClient apiClient = new ApiClient();
        RestClient restClientAPI = apiClient.getClient();

        JsonClient jsonClient = new JsonClient();
        JSONObject jsonObject = jsonClient.getCustomerDetailsJson(logInResList.get(0).getCompId(), logInResList.get(0).getLoginId(),
                customerId, logInResList.get(0).getToken());

        RequestBody body = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());

        Call<GetCustomerDetailsPOJO> call = restClientAPI.getCustomerDetails(body);
        call.enqueue(new retrofit2.Callback<GetCustomerDetailsPOJO>() {
            @Override
            public void onResponse(@NonNull Call<GetCustomerDetailsPOJO> call, @NonNull Response<GetCustomerDetailsPOJO> response) {
                GetCustomerDetailsPOJO getCustomerDetailsPOJO = response.body();
                int code = response.code();
                Log.e(TAG, "code>>>>" + code);
                if (code == RESPONSE_CODE) {
                    if (getCustomerDetailsPOJO != null) {
                        if (getCustomerDetailsPOJO.getResponseCode().equals(API_RESPONSE_CODE_PUNCH_IN_OUT)) {
                            if (getCustomerDetailsPOJO.getResponseStatus().equals(TRUE)) {
                                if (!getCustomerDetailsPOJO.getData().get(0).getVisiting_card().equals("")) {
                                    tvVisitingCardFront.setText(getCustomerDetailsPOJO.getData().get(0).getVisiting_card());
                                } else {
                                    tvVisitingCardFront.setText(null);
                                }

                                if (!getCustomerDetailsPOJO.getData().get(0).getVisiting_card_back().equals("")) {
                                    tvVisitingCardBack.setText(getCustomerDetailsPOJO.getData().get(0).getVisiting_card_back());
                                } else {
                                    tvVisitingCardBack.setText(null);
                                }

                                if (!getCustomerDetailsPOJO.getData().get(0).getId_Card_Proof().equals("")) {
                                    tvIDCardDocument.setText(getCustomerDetailsPOJO.getData().get(0).getId_Card_Proof());
                                } else {
                                    tvIDCardDocument.setText(null);
                                }

                                if (!getCustomerDetailsPOJO.getData().get(0).getAddress_Proof().equals("")) {
                                    tvAddressProof.setText(getCustomerDetailsPOJO.getData().get(0).getAddress_Proof());
                                } else {
                                    tvAddressProof.setText(null);
                                }

                                if (!getCustomerDetailsPOJO.getData().get(0).getGst_Certificate().equals("")) {
                                    tvGSTCertificate.setText(getCustomerDetailsPOJO.getData().get(0).getGst_Certificate());
                                } else {
                                    tvGSTCertificate.setText(null);
                                }
                            } else {
                                showMessage(getCustomerDetailsPOJO.getResponseMessage());
                            }
                        } else {
                            showMessage(getCustomerDetailsPOJO.getResponseMessage());
                        }
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<GetCustomerDetailsPOJO> call, @NonNull Throwable t) {
                Log.e(TAG, "Throwable>>>>" + t.getMessage());
                showMessage(getString(R.string.internet_not_available));
            }
        });
    }
}