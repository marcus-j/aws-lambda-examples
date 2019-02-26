package de.marcusjanke.examples.aws.lambda.s3;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.S3Event;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.event.S3EventNotification.S3EventNotificationRecord;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;

import net.coobird.thumbnailator.Thumbnails;

/**
 * ThumbnailLambdaFunctionHandler
 * 
 * based on
 * {@link http://docs.aws.amazon.com/lambda/latest/dg/with-s3-example.html}
 * 
 * @author marcus
 * 
 *         [project stub created with AWS Toolkit for Eclipse]
 *
 */
public class ThumbnailLambdaFunctionHandler implements RequestHandler<S3Event, String> {
		
	private static final String DST_BUCKET = "image-bucket-dst";

	private static final int MAX_WIDTH = 120;
	private static final int MAX_HEIGHT = 240;

	private static final String JPG_TYPE = (String) "jpg";
	private static final String PNG_TYPE = (String) "png";
	
	private static final String JPG_MIME = (String) "image/jpeg";
	private static final String PNG_MIME = (String) "image/png";

	private final AmazonS3 s3;

	/**
	 * new ThumbnailLambdaFunctionHandler with default AmazonS3 client
	 */
	public ThumbnailLambdaFunctionHandler() {
		s3 = AmazonS3ClientBuilder.standard().build();
	}

	/**
	 * Test purpose only.
	 * 
	 * @param s3
	 */
	ThumbnailLambdaFunctionHandler(AmazonS3 s3) {
		this.s3 = s3;
	}

	/**
	 * handle request
	 */
	@Override
	public String handleRequest(S3Event s3event, Context context) {
		LambdaLogger logger = context.getLogger();
		try {
			S3EventNotificationRecord record = s3event.getRecords().get(0);
			String srcBucket = record.getS3().getBucket().getName();
			String dstBucket = DST_BUCKET;
			String srcKey = record.getS3().getObject().getKey().replace('+', ' ');
			srcKey = URLDecoder.decode(srcKey, "UTF-8");
			String dstKey = srcKey;
			String imageType = getImageType(srcKey);
			if (isApplicableImageType(imageType)) {
				S3Object s3Object = s3.getObject(new GetObjectRequest(srcBucket, srcKey));
				InputStream objectData = s3Object.getObjectContent();
				ByteArrayOutputStream os = new ByteArrayOutputStream();
				Thumbnails.of(objectData).size(MAX_WIDTH, MAX_HEIGHT).toOutputStream(os);
				InputStream is = new ByteArrayInputStream(os.toByteArray());
				ObjectMetadata meta = createThumbnailMetaData(imageType, os.size());
				s3.putObject(dstBucket, dstKey, is, meta);
				logger.log(String.format("Created thumbnail %s in bucket %s from source image %s in bucket %s ", dstKey, dstBucket, srcKey, srcBucket));
				return "Ok";
			} else {
				logger.log(String.format("Could not create thumbnail %s in bucket %s from source image %s in bucket %s ", dstKey, dstBucket, srcKey, srcBucket));
				return "Not an image";
			}
		} catch (IOException e) {
			logger.log(e.getMessage());
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * get image type suffix
	 * 
	 * @param objectKey
	 * @return image type suffix
	 */
	private String getImageType(String objectKey) {
		Matcher matcher = Pattern.compile(".*\\.([^\\.]*)").matcher(objectKey);
		if (!matcher.matches()) {
			return null;
		} else {
			return matcher.group(1);
		}
	}

	/**
	 * checks if image matches JPG or PNG type
	 * 
	 * @param imageType
	 * @return true if JPG or PNG
	 */
	private boolean isApplicableImageType(String imageType) {
		if (!(JPG_TYPE.equalsIgnoreCase(imageType)) && !(PNG_TYPE.equalsIgnoreCase(imageType))) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * creates thumbnail metadata
	 * 
	 * @param imageType
	 * @param contentLength
	 * @return thumbnail metadata
	 */
	private ObjectMetadata createThumbnailMetaData(String imageType, long contentLength) {
		ObjectMetadata meta = new ObjectMetadata();
		meta.setContentLength(contentLength);
		if (JPG_TYPE.equals(imageType)) {
			meta.setContentType(JPG_MIME);
		} else if (PNG_TYPE.equals(imageType)) {
			meta.setContentType(PNG_MIME);
		}
		return meta;
	}
}