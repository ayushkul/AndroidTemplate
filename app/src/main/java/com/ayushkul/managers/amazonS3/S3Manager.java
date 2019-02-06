package com.ayushkul.managers.amazonS3;

import android.content.Context;
import android.util.Log;

import com.amazonaws.HttpMethod;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferListener;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferObserver;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferState;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.ayushkul.constants.AppConstants;
import com.ayushkul.constants.EventConstants;
import com.ayushkul.models.EventModel;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.net.URL;
import java.util.Date;

/**
 * Created by Ayush Kulshrestha on 06/02/19.
 * This is the S3 manager to get the config file fom S3-
 */

public class S3Manager {

    private static AmazonS3Client sS3Client;
    private static TransferUtility sTransferUtility;

    /**
     * Gets an instance of a S3 client which is constructed using the given
     * Context.
     */
    public static AmazonS3Client getS3Client() {

        //Decrypt the access key and secret key-

        String accessKey = AppConstants.BUCKET_ACCESS_KEY;
        String secretKey = AppConstants.BUCKET_SECRET_KEY;

        if (sS3Client == null) {
            AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);
            sS3Client = new AmazonS3Client(credentials);
            sS3Client.setRegion(Region.getRegion(Regions.fromName(AppConstants.BUCKET_REGION)));
        }
        return sS3Client;
    }

    /**
     * @param objectKey : fileName of S3 file-
     * @return Signed URL for next 1 hr-
     */
    public static URL generatePreSignedUrl(String objectKey) {
        getS3Client();
        if (sS3Client == null)
            return null;
        Date expiration = new Date();
        long expTimeMillis = expiration.getTime();
        expTimeMillis += 1000 * 60 * 60;
        expiration.setTime(expTimeMillis);
        GeneratePresignedUrlRequest urlRequest = new GeneratePresignedUrlRequest(AppConstants.BUCKET_NAME, objectKey).withMethod(HttpMethod.GET)
                .withExpiration(expiration);
        return sS3Client.generatePresignedUrl(urlRequest);
    }


    /**
     * Gets an instance of the TransferUtility which is constructed using the
     * given Context
     *
     * @param context contxt for TransferUtility-
     * @return a TransferUtility instance
     */
    public static TransferUtility getTransferUtility(Context context) {
        if (context == null)
            return null;
        if (sTransferUtility == null) {
            sTransferUtility = TransferUtility.builder()
                    .context(context.getApplicationContext())
                    .s3Client(getS3Client())
                    .defaultBucket(AppConstants.BUCKET_NAME)
                    .build();
        }
        return sTransferUtility;
    }

    public static void uploadWithTransferUtility(final File file, final String fileName, final String EventName) {
        TransferObserver uploadObserver = sTransferUtility.upload(fileName, file);
        uploadObserver.setTransferListener(new TransferListener() {
            @Override
            public void onStateChanged(int id, TransferState state) {
                if (TransferState.COMPLETED == state) {
                    Log.d("File Upload", "Complete");
                    EventBus.getDefault().post(new EventModel(null, fileName, EventConstants.S3_FILE_UPLOAD_SUCCESS, null));
                }
            }

            @Override
            public void onProgressChanged(int id, long bytesCurrent, long bytesTotal) {
                int progress = (int) ((double) bytesCurrent * 100 / bytesTotal);
                Log.d("Uploading", String.valueOf(progress + "%"));
                Log.d("fileName", String.valueOf(file.getName()));
                Log.d("fileKey", String.valueOf(fileName));
            }

            @Override
            public void onError(int id, Exception ex) {
                EventBus.getDefault().post(new EventModel(null, null, EventConstants.S3_FILE_UPLOAD_FAILURE, null));
            }
        });

    }

}
