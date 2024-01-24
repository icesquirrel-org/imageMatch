package com.example.demo.sample.web;

import java.util.ArrayList;
import java.util.List;

import org.opencv.core.Core;
import org.opencv.core.Mat;
//import org.opencv.core.MatOfFloat;
//import org.opencv.core.MatOfInt;
//import org.opencv.core.Range;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import java.util.Map;

import org.springframework.stereotype.Service;



@Service
public class SampleServiceImpl{ 
	
	public List<Map<String, Object>> getMetaDataList(Map<String, Object> params) {
		List<Map<String, Object>>  rs = new ArrayList<Map<String, Object>> ();
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
//		
//	 
////		   Mat srcBase = Imgcodecs.imread(args[0]);
////	        Mat srcTest1 = Imgcodecs.imread(args[1]);
////	        Mat srcTest2 = Imgcodecs.imread(args[2]);
        Mat  srcBase = Imgcodecs.imread("./img/Samsung_Orig_Wordmark_BLUE_RGB.png");
//	        		 
        Mat hsvBase = new Mat(), hsvTest1 = new Mat(), hsvTest2 = new Mat();
        Imgproc.cvtColor( srcBase, hsvBase, Imgproc.COLOR_BGR2HSV );
//        Imgproc.cvtColor( srcTest1, hsvTest1, Imgproc.COLOR_BGR2HSV );
//        Imgproc.cvtColor( srcTest2, hsvTest2, Imgproc.COLOR_BGR2HSV );
		return rs;
	}

}
