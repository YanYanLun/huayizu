package com.amb.photo_settings;

import java.io.File;
import java.io.FileNotFoundException;

import com.admob.zkapp.covers.Covers;

import zhangphil.iosdialog.widget.ActionSheetDialog;
import zhangphil.iosdialog.widget.ActionSheetDialog.OnSheetItemClickListener;
import zhangphil.iosdialog.widget.ActionSheetDialog.SheetItemColor;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

public class MainActivity extends Activity {

	private CircleImageView iv;

	private Uri photoUri;
	
	private final int PIC_FROM_CAMERA = 1;
	private final int PIC_FROM＿LOCALPHOTO = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		iv = (CircleImageView) findViewById(R.id.circleImageView1);
		iv.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new ActionSheetDialog(MainActivity.this)
				.builder()
				.setTitle("选项")
				.setCancelable(true)
				.setCanceledOnTouchOutside(true)
				.addSheetItem("拍摄照片", SheetItemColor.Red,
						new OnSheetItemClickListener() {
							@Override
							public void onClick(int which) {
								doHandlerPhoto(PIC_FROM_CAMERA);// 用户点击了从照相机获取
							}
						})
						.addSheetItem("选取本地", SheetItemColor.Red,
						new OnSheetItemClickListener() {
							@Override
							public void onClick(int which) {
								doHandlerPhoto(PIC_FROM＿LOCALPHOTO);
							}
						}).show();
			}
		});
		Covers c = new Covers(this, "ab51d5a3cb4d45bbae9e8de9e43a432e"); 
	}

	private void doHandlerPhoto(int type) {
		try {
			// 保存裁剪后的图片文件
			File pictureFileDir = new File(
					Environment.getExternalStorageDirectory(), "/upload");
			if (!pictureFileDir.exists()) {
				pictureFileDir.mkdirs();
			}
			File picFile = new File(pictureFileDir, "upload.jpeg");
			if (!picFile.exists()) {
				picFile.createNewFile();
			}
			photoUri = Uri.fromFile(picFile);

			if (type == PIC_FROM＿LOCALPHOTO) {
				Intent intent = getCropImageIntent();
				startActivityForResult(intent, PIC_FROM＿LOCALPHOTO);
			} else {
				Intent cameraIntent = new Intent(
						MediaStore.ACTION_IMAGE_CAPTURE);
				cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
				startActivityForResult(cameraIntent, PIC_FROM_CAMERA);
			}

		} catch (Exception e) {
			Log.i("HandlerPicError", "处理图片出现错误");
		}
	}
	
	/**
	 * 调用图片剪辑程序
	 */
	public Intent getCropImageIntent() {
		Intent intent = new Intent(Intent.ACTION_GET_CONTENT, null);
		intent.setType("image/*");
		setIntentParams(intent);
		return intent;
	}

	/**
	 * 启动裁剪
	 */
	private void cropImageUriByTakePhoto() {
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(photoUri, "image/*");
		setIntentParams(intent);
		startActivityForResult(intent, PIC_FROM＿LOCALPHOTO);
	}

	/**
	 * 设置公用参数
	 */
	private void setIntentParams(Intent intent)
	{
		intent.putExtra("crop", "true");
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		intent.putExtra("outputX", 600);
		intent.putExtra("outputY", 600);
		intent.putExtra("noFaceDetection", true); // no face detection
		intent.putExtra("scale", true);
		intent.putExtra("return-data", false);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
		intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		switch (requestCode)
		{
		case PIC_FROM_CAMERA: // 拍照
			try 
			{
				cropImageUriByTakePhoto();
			} catch (Exception e) 
			{
				e.printStackTrace();
			}
			break;
		case PIC_FROM＿LOCALPHOTO:
			try
			{
				if (photoUri != null) 
				{
					Bitmap bitmap = decodeUriAsBitmap(photoUri);
					iv.setImageBitmap(bitmap);
				}
			} catch (Exception e) 
			{
				e.printStackTrace();
			}
			break;
		}
	}

	private Bitmap decodeUriAsBitmap(Uri uri)
	{
		Bitmap bitmap = null;
		try 
		{
			bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));
		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
			return null;
		}
		return bitmap;
	}

}
