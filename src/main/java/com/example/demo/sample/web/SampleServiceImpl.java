package com.example.demo.sample.web;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.springframework.stereotype.Service;

import nu.pattern.OpenCV;

import org.apache.commons.io.IOUtils;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.MatOfFloat;
import org.opencv.core.MatOfInt;
import org.opencv.core.Range;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

@Service
public class SampleServiceImpl{ 
	
	static{ OpenCV.loadShared(); }
	public List<Map<String, Object>> getCompareResult(Map<String, Object> params) {
		
		List<Map<String, Object>>  rs = new ArrayList<Map<String, Object>> ();
		   
        // hue varies from 0 to 179, saturation from 0 to 255
        int hBins = 180, sBins = 256;
        int[] histSize = { hBins, sBins };
        // hue varies from 0 to 179, saturation from 0 to 255
        float[] ranges = { 0, 180, 0, 256 };
        // Use the 0-th and 1-st channels
        // H,S 채널에 대한 히스토그램 
        int[] channels = { 0, 1 };
		
		try {
			String sourceUrl = (String) params.get("source");
			List<String> targetList = (List<String>) params.get("targetList");
			
			InputStream source = new URL(sourceUrl).openStream();
		    byte[] imgData = IOUtils.toByteArray(source);
		    Mat srcBase = Imgcodecs.imdecode(new MatOfByte(imgData), Imgcodecs.IMREAD_UNCHANGED);
		    Mat hsvBase = new Mat();
		    Mat histBase = new Mat();
		    Imgproc.cvtColor( srcBase, hsvBase, Imgproc.COLOR_BGR2HSV );
	        List<Mat> hsvBaseList = Arrays.asList(hsvBase);
	        Imgproc.calcHist(hsvBaseList, new MatOfInt(channels), new Mat(), histBase, new MatOfInt(histSize), new MatOfFloat(ranges), false);
	        Core.normalize(histBase, histBase, 0, 1, Core.NORM_MINMAX);
		    
			for(int i = 0 ; i < targetList.size() ; i++) {
				Map<String, Object> rsMap = new HashMap<String, Object>();
				rsMap.put("source", sourceUrl);
				String targetUrl = targetList.get(i);
				rsMap.put("target", targetUrl);
				
				InputStream target = new URL(targetUrl).openStream();
			    byte[] targetimgData = IOUtils.toByteArray(target);
				Mat mattarget = Imgcodecs.imdecode(new MatOfByte(targetimgData),Imgcodecs.IMREAD_UNCHANGED);
				
				List<Mat> targetHSVList  = new ArrayList<Mat> ();
				Mat hsvTarget = new Mat();
				Imgproc.cvtColor( mattarget, hsvTarget, Imgproc.COLOR_BGR2HSV );
				targetHSVList.add(hsvTarget);
				Mat histTarget = new Mat();
				Imgproc.calcHist(targetHSVList, new MatOfInt(channels), new Mat(), histTarget, new MatOfInt(histSize), new MatOfFloat(ranges), false);
		        Core.normalize(histTarget, histTarget, 0, 1, Core.NORM_MINMAX);
		        
		        for( int compareMethod = 0; compareMethod < 4; compareMethod++ ) {
		            double compResult = Imgproc.compareHist( histBase, histTarget, compareMethod );
		            rsMap.put("compareMethod_"+compareMethod, compResult);
		        }
		        rs.add(rsMap);
			}
			
		} catch (MalformedURLException e) {
		} catch (IOException e) {
		}
		
		
		return rs;
	}
	public static void main(String[] args) {
//		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		SampleServiceImpl.run2();
// 		System. 
	}
	public static void run2() {
		//opencv.loadshared();
		OpenCV.loadShared();
		InputStream source;
		
		try {
			source = new URL("http://localhost:8080/img/Samsung_Orig_Wordmark_BLUE_RGB.png").openStream();
		    byte[] imgData = IOUtils.toByteArray(source);
		    Mat srcBase = Imgcodecs.imdecode(new MatOfByte(imgData), Imgcodecs.IMREAD_UNCHANGED);
		    
	        InputStream ts1 = new URL("http://localhost:8080/img/samlogo1small.png").openStream();
	        byte[] ts1data = IOUtils.toByteArray(ts1);
	        Mat srcTest1 = Imgcodecs.imdecode(new MatOfByte(ts1data), Imgcodecs.IMREAD_UNCHANGED);
	        InputStream ts2 = new URL("http://localhost:8080/img/Samsung_Orig_Wordmark_WHITE_RGB.jpg").openStream();
	        byte[] ts2data = IOUtils.toByteArray(ts2);
	        Mat srcTest2 = Imgcodecs.imdecode(new MatOfByte(ts2data), Imgcodecs.IMREAD_UNCHANGED);
	        
	        // Mat srcTest2 = Imgcodecs.imread("/Users/icesquirrel/workspace/ecoletree/imageMatch/imageMatch/src/main/resources/static/img/samlogo1small.png");
	        if (srcBase.empty()|| srcTest1.empty() || srcTest2.empty()) {
	            System.err.println("Cannot read the images");
	            System.exit(0);
	        }
	        
	        Mat hsvBase = new Mat(); 
			Mat hsvTest1 = new Mat();
			Mat hsvTest2 = new Mat();
	        
	        Imgproc.cvtColor( srcBase, hsvBase, Imgproc.COLOR_BGR2HSV );
	        Imgproc.cvtColor( srcTest1, hsvTest1, Imgproc.COLOR_BGR2HSV );
	        Imgproc.cvtColor( srcTest2, hsvTest2, Imgproc.COLOR_BGR2HSV );
	        
	        int hBins = 50, sBins = 60;
	        int[] histSize = { hBins, sBins };
	        // hue varies from 0 to 179, saturation from 0 to 255
	        float[] ranges = { 0, 180, 0, 256 };
	        // Use the 0-th and 1-st channels
	        // H,S 채널에 대한 히스토그램 
	        int[] channels = { 0, 1 };
	        Mat histBase = new Mat(),  histTest1 = new Mat(), histTest2 = new Mat();
	        
	        List<Mat> hsvBaseList = Arrays.asList(hsvBase);
	        //      calcHist(List<Mat> images, MatOfInt channels,      Mat mask,  Mat hist, MatOfInt histSize,      MatOfFloat ranges,      boolean accumulate) {
	        Imgproc.calcHist(hsvBaseList,      new MatOfInt(channels), new Mat(), histBase, new MatOfInt(histSize), new MatOfFloat(ranges), false);
	        Core.normalize(histBase, histBase, 0, 1, Core.NORM_MINMAX);
	        
//	        List<Mat> hsvHalfDownList = Arrays.asList(hsvHalfDown);
//	        Imgproc.calcHist(hsvHalfDownList, new MatOfInt(channels), new Mat(), histHalfDown, new MatOfInt(histSize), new MatOfFloat(ranges), false);
//	        Core.normalize(histHalfDown, histHalfDown, 0, 1, Core.NORM_MINMAX);
	        
	        List<Mat> hsvTest1List = Arrays.asList(hsvTest1);
	        Imgproc.calcHist(hsvTest1List, new MatOfInt(channels), new Mat(), histTest1, new MatOfInt(histSize), new MatOfFloat(ranges), false);
	        Core.normalize(histTest1, histTest1, 0, 1, Core.NORM_MINMAX);
	        
	        List<Mat> hsvTest2List = Arrays.asList(hsvTest2);
	        Imgproc.calcHist(hsvTest2List, new MatOfInt(channels), new Mat(), histTest2, new MatOfInt(histSize), new MatOfFloat(ranges), false);
	        Core.normalize(histTest2, histTest2, 0, 1, Core.NORM_MINMAX);
	        
	        // C++: enum HistCompMethods (cv.HistCompMethods) compareMethod
//	        public static final int
//	                HISTCMP_CORREL = 0,
//	                HISTCMP_CHISQR = 1,
//	                HISTCMP_INTERSECT = 2,
//	                HISTCMP_BHATTACHARYYA = 3,
//	                HISTCMP_HELLINGER = HISTCMP_BHATTACHARYYA,
//	                HISTCMP_CHISQR_ALT = 4,
//	                HISTCMP_KL_DIV = 5;
	        
	        for( int compareMethod = 0; compareMethod < 4; compareMethod++ ) {
	            double baseBase = Imgproc.compareHist( histBase, histBase, compareMethod );
//	            double baseHalf = Imgproc.compareHist( histBase, histHalfDown, compareMethod );
	            double baseTest1 = Imgproc.compareHist( histBase, histTest1, compareMethod );
	            double baseTest2 = Imgproc.compareHist( histBase, histTest2, compareMethod );
	            System.out.println("Method " + compareMethod + " Perfect, Base-Test(1), Base-Test(2) : " + baseBase + " / "  + baseTest1 + " / " + baseTest2);
	        }
//			byte[] bytesSource = source.readAllBytes();
//			MatOfByte srcMap = new MatOfByte();
//			srcMap.put(0,0,bytesSource);
//			Mat matSource = Imgcodecs.imdecode(srcMap,Imgcodecs.IMREAD_UNCHANGED);
			 
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
    }
	
	public static void run() {
        Mat srcBase = Imgcodecs.imread("/Users/icesquirrel/workspace/ecoletree/imageMatch/imageMatch/src/main/resources/static/img/Samsung_Orig_Wordmark_BLUE_RGB.png");
        Mat srcTest1 = Imgcodecs.imread("/Users/icesquirrel/workspace/ecoletree/imageMatch/imageMatch/src/main/resources/static/img/Samsung_Orig_Wordmark_WHITE_RGB.png");
        Mat srcTest2 = Imgcodecs.imread("/Users/icesquirrel/workspace/ecoletree/imageMatch/imageMatch/src/main/resources/static/img/samlogo1small.png");
        if (srcBase.empty() || srcTest1.empty() || srcTest2.empty()) {
            System.err.println("Cannot read the images");
            System.exit(0);
        }
        Mat hsvBase = new Mat(); 
		Mat hsvTest1 = new Mat();
		Mat hsvTest2 = new Mat();
        
        Imgproc.cvtColor( srcBase, hsvBase, Imgproc.COLOR_BGR2HSV );
        Imgproc.cvtColor( srcTest1, hsvTest1, Imgproc.COLOR_BGR2HSV );
        Imgproc.cvtColor( srcTest2, hsvTest2, Imgproc.COLOR_BGR2HSV );
        
       // Mat hsvHalfDown = hsvBase.submat( new Range( hsvBase.rows()/2, hsvBase.rows() - 1 ), new Range( 0, hsvBase.cols() - 1 ) );
        
        // hue varies from 0 to 179, saturation from 0 to 255
        int hBins = 180, sBins = 256;
        int[] histSize = { hBins, sBins };
        // hue varies from 0 to 179, saturation from 0 to 255
        float[] ranges = { 0, 180, 0, 256 };
        // Use the 0-th and 1-st channels
        // H,S 채널에 대한 히스토그램 
        int[] channels = { 0, 1 };
        Mat histBase = new Mat(); 
        // Mat histHalfDown = new Mat();
        Mat histTest1 = new Mat();
        Mat histTest2 = new Mat();
        
        List<Mat> hsvBaseList = Arrays.asList(hsvBase);
        Imgproc.calcHist(hsvBaseList, new MatOfInt(channels), new Mat(), histBase, new MatOfInt(histSize), new MatOfFloat(ranges), false);
        Core.normalize(histBase, histBase, 0, 1, Core.NORM_MINMAX);
        
//        List<Mat> hsvHalfDownList = Arrays.asList(hsvHalfDown);
//        Imgproc.calcHist(hsvHalfDownList, new MatOfInt(channels), new Mat(), histHalfDown, new MatOfInt(histSize), new MatOfFloat(ranges), false);
//        Core.normalize(histHalfDown, histHalfDown, 0, 1, Core.NORM_MINMAX);
        
        
        List<Mat> hsvTest1List = Arrays.asList(hsvTest1);
        Imgproc.calcHist(hsvTest1List, new MatOfInt(channels), new Mat(), histTest1, new MatOfInt(histSize), new MatOfFloat(ranges), false);
        Core.normalize(histTest1, histTest1, 0, 1, Core.NORM_MINMAX);
        List<Mat> hsvTest2List = Arrays.asList(hsvTest2);
        Imgproc.calcHist(hsvTest2List, new MatOfInt(channels), new Mat(), histTest2, new MatOfInt(histSize), new MatOfFloat(ranges), false);
        Core.normalize(histTest2, histTest2, 0, 1, Core.NORM_MINMAX);
        // C++: enum HistCompMethods (cv.HistCompMethods) compareMethod
//      public static final int
//              HISTCMP_CORREL = 0,
//              HISTCMP_CHISQR = 1,
//              HISTCMP_INTERSECT = 2,
//              HISTCMP_BHATTACHARYYA = 3,
//              HISTCMP_HELLINGER = HISTCMP_BHATTACHARYYA,
//              HISTCMP_CHISQR_ALT = 4,
//              HISTCMP_KL_DIV = 5;
        
        for( int compareMethod = 0; compareMethod < 4; compareMethod++ ) {
            double baseBase = Imgproc.compareHist( histBase, histBase, compareMethod );
           // double baseHalf = Imgproc.compareHist( histBase, histHalfDown, compareMethod );
            double baseTest1 = Imgproc.compareHist( histBase, histTest1, compareMethod );
            double baseTest2 = Imgproc.compareHist( histBase, histTest2, compareMethod );
            System.out.println("Method " + compareMethod + " self,  Base-Test(1), Base-Test(2) : " + baseBase + " / " + baseTest1 + " / " + baseTest2);
        }
    }
}
