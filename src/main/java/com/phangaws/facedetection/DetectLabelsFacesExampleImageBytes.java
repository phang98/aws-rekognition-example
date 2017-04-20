package com.phangaws.facedetection;
        
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.List;
import com.amazonaws.services.rekognition.AmazonRekognition;
import com.amazonaws.services.rekognition.AmazonRekognitionClientBuilder;
import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.rekognition.model.AmazonRekognitionException;
import com.amazonaws.services.rekognition.model.DetectFacesRequest;
import com.amazonaws.services.rekognition.model.DetectFacesResult;
import com.amazonaws.services.rekognition.model.DetectLabelsRequest;
import com.amazonaws.services.rekognition.model.DetectLabelsResult;
import com.amazonaws.services.rekognition.model.FaceDetail;
import com.amazonaws.services.rekognition.model.Image;
import com.amazonaws.services.rekognition.model.Label;
import com.amazonaws.util.IOUtils;

public class DetectLabelsFacesExampleImageBytes {
    public static void main(String[] args) throws Exception {
    	String photo="aaron.jpg";
        ClassLoader classLoader = new DetectLabelsFacesExampleImageBytes().getClass().getClassLoader();
        
        AWSCredentials credentials;
        try {
            credentials = new ProfileCredentialsProvider().getCredentials();
        } catch (Exception e) {
            throw new AmazonClientException("Cannot load the credentials from the credential profiles file. "
                    + "Please make sure that your credentials file is at the correct "
                    + "location (/Usersuserid.aws/credentials), and is in a valid format.", e);
        }
        ByteBuffer imageBytes;
        try (InputStream inputStream = classLoader.getResourceAsStream(photo)) {
            imageBytes = ByteBuffer.wrap(IOUtils.toByteArray(inputStream));
        }


        AmazonRekognition rekognitionClient = AmazonRekognitionClientBuilder
          		.standard()
          		.withRegion(Regions.US_WEST_2)
        		.withCredentials(new AWSStaticCredentialsProvider(credentials))
        		.build();

        displayFaceDetail(imageBytes, rekognitionClient, photo);
        //displayLabel(imageBytes, rekognitionClient, photo);

    }

    private static void displayFaceDetail(ByteBuffer imageBytes, AmazonRekognition rekognitionClient, String photo) {
        DetectFacesRequest dfRequest = new DetectFacesRequest()
                .withImage(new Image()
                        .withBytes(imageBytes))
                .withAttributes("ALL");
        
        try {

            DetectFacesResult result = rekognitionClient.detectFaces(dfRequest);
            List<FaceDetail> faceDetails = result.getFaceDetails();

            System.out.println("Detected Face for " + photo);
            for (FaceDetail facedetail: faceDetails) {
                System.out.println("Face Detail: " + facedetail.toString());
            }

        } catch (AmazonRekognitionException e) {
            e.printStackTrace();
        }
    }

    private static void displayLabel(ByteBuffer imageBytes, AmazonRekognition rekognitionClient, String photo) {
        DetectLabelsRequest request = new DetectLabelsRequest()
                .withImage(new Image()
                        .withBytes(imageBytes))
                .withMaxLabels(10)
                .withMinConfidence(77F);

        try {

            DetectLabelsResult result = rekognitionClient.detectLabels(request);
            List <Label> labels = result.getLabels();

            System.out.println("Detected labels for " + photo);
            for (Label label: labels) {
                System.out.println(label.getName() + ": " + label.getConfidence().toString());
            }

        } catch (AmazonRekognitionException e) {
            e.printStackTrace();
        }
    }
}
