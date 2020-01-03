package com.imooc.o2o.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.imooc.o2o.dto.ImageHolder;
import com.imooc.o2o.entity.ProductImg;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;

public class ImageUtil {
	private static Logger logger=LoggerFactory.getLogger(ImageUtil.class);
	private static	String basePath=Thread.currentThread().getContextClassLoader().getResource("").getPath();
	private static final SimpleDateFormat sDateFormat=new SimpleDateFormat("yyyyMMddHHmmss");
	private static final Random r=new Random();
	public static String generateThumbnail(InputStream thumbnailInputStream,String fileName,String targetAddr) {
		String realFileName=getRandomFileName();
		String extension=getFileExtension(fileName);
		makeDirPath(targetAddr);
		String relativeAddr=targetAddr+realFileName+extension;
		File dest=new File(PathUtil.getImgBasePath()+relativeAddr);
		// TODO Auto-generated method stub
		try {
			Thumbnails.of(thumbnailInputStream)
			.size(200, 200)
			.watermark(Positions.BOTTOM_RIGHT,
					ImageIO.read(new File(basePath+"/right.gif")),0.25f)
			.outputQuality(0.8f).toFile(dest);
		} catch (IOException e) {
			// TODO: handle exception
		}
		return relativeAddr;
	}
	private static void makeDirPath(String targetAddr) {
		// TODO Auto-generated method stub
		String realFileParentPath = PathUtil.getImgBasePath()+targetAddr;
		File dirPath=new File(realFileParentPath);
		if(!dirPath.exists()){
			dirPath.mkdirs();
		}
	}
	private static String getFileExtension(String fileName) {
		return fileName.substring(fileName.lastIndexOf("."));
	}
	private static String getRandomFileName() {
		int rannum=r.nextInt(89999)+10000;
		String nowTimeStr=sDateFormat.format(new Date());
		return nowTimeStr+rannum;
	}
	public static String generateThumbnail(ImageHolder thumbnail, String targetAddr) {
		String realFileName=getRandomFileName();
		String extension=getFileExtension(thumbnail.getImageName());
		makeDirPath(targetAddr);
		String relativeAddr=targetAddr+realFileName+extension;
		logger.debug("current relativeAddr is:"+relativeAddr);
		File dest=new File(PathUtil.getImgBasePath()+relativeAddr);
		logger.debug("current complete addr is:"+PathUtil.getImgBasePath()+relativeAddr);
		// TODO Auto-generated method stub
		try {
			Thumbnails.of(thumbnail.getImage())
			.size(200, 200)
			.watermark(Positions.BOTTOM_RIGHT,
					ImageIO.read(new File(basePath+"/right.gif")),0.25f)
			.outputQuality(0.8f).toFile(dest);
		} catch (IOException e) {
			// TODO: handle exception
			logger.error(e.toString());
			e.printStackTrace();
		}
		return relativeAddr;
	}
	private static String getFileExtension(File thumbnail) {
		String originalFileName=thumbnail.getAbsolutePath();
		return originalFileName.substring(originalFileName.lastIndexOf("."));
	}
	
	public static void deleteFileOrPath(String srcPath) {
		File fileOrPath=new File(PathUtil.getImgBasePath()+srcPath);
		if(fileOrPath.exists()) {
			if(fileOrPath.isDirectory()) {
				for (File file : fileOrPath.listFiles()) {
					file.delete();
				}
			}
			fileOrPath.delete();
		}
	}
	public static String generateThumbnail(CommonsMultipartFile thumbnail, String targetAddr) {
		String realFileName = FileUtil.getRandomFileName();
		String extension = getFileExtension(thumbnail.getOriginalFilename());
		makeDirPath(targetAddr);
		String relativeAddr = targetAddr + realFileName + extension;
		File dest = new File(FileUtil.getImgBasePath() + relativeAddr);
		try {
			Thumbnails.of(thumbnail.getInputStream()).size(337, 640).outputQuality(0.5f).toFile(dest);
		} catch (IOException e) {
			throw new RuntimeException("创建缩略图失败：" + e.toString());
		}
		return relativeAddr;
	}
	public static String generateNormalImgs(ImageHolder imageHolder, String targetAddr) {
		String realFileName=getRandomFileName();
		String extension=getFileExtension(imageHolder.getImageName());
		makeDirPath(targetAddr);
		String relativeAddr=targetAddr+realFileName+extension;
		logger.debug("current relativeAddr is:"+relativeAddr);
		File dest=new File(PathUtil.getImgBasePath()+relativeAddr);
		logger.debug("current complete addr is:"+PathUtil.getImgBasePath()+relativeAddr);
		// TODO Auto-generated method stub
		try {
			Thumbnails.of(imageHolder.getImage())
			.size(200, 200)
			.watermark(Positions.BOTTOM_RIGHT,
					ImageIO.read(new File(basePath+"/right.gif")),0.25f)
			.outputQuality(0.8f).toFile(dest);
		} catch (IOException e) {
			// TODO: handle exception
			logger.error(e.toString());
			e.printStackTrace();
		}
		return relativeAddr;
	}
}
