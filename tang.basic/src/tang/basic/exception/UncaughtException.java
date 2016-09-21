package tang.basic.exception;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Environment;
import android.os.Looper;
 
import android.util.Log;

/**
 * ����ȫ���쳣,��Ϊ�е��쳣���ǲ��񲻵�
 * 
 * @author river
 * 
 */
public class UncaughtException implements UncaughtExceptionHandler {
    private final static String TAG = "UncaughtException";
    private static UncaughtException mUncaughtException;
    private Context context;
    private DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");  
    // �����洢�豸��Ϣ���쳣��Ϣ  
    private Map<String, String> infos = new HashMap<String, String>();  
    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    private UncaughtException() {
        // TODO Auto-generated constructor stub
    }

    /**
     * ͬ�����������ⵥ�����̻߳����³����쳣
     * 
     * @return
     */
    public synchronized static UncaughtException getInstance() {
        if (mUncaughtException == null) {
            mUncaughtException = new UncaughtException();
        }
        return mUncaughtException;
    }

    /**
     * ��ʼ�����ѵ�ǰ�������ó�UncaughtExceptionHandler������
     */
    public void init() {
        Thread.setDefaultUncaughtExceptionHandler(mUncaughtException);
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        // TODO Auto-generated method stub
        //�����쳣,���ǻ����԰��쳣��Ϣд���ļ����Թ�����������
       saveCrashInfo2File(ex);
    	Log.e(TAG, "uncaughtException thread : " + thread + "||name=" + thread.getName() + "||id=" + thread.getId() + "||exception=" + ex);
   /*   Looper.prepare();
    	Toast.makeText(context, "�����쳣�������˳�", 1).show();
      System.exit(0);
    	Looper.loop();*/
    	
    	 showDialog() ;
    }

    private void showDialog() {
        new Thread() {
            @Override
            public void run() {
            	//������׳��쳣�������������������Ӧ��
                Looper.prepare();
                new AlertDialog.Builder(context).setTitle("��ʾ").setCancelable(false).setMessage("��ү�ұ�����...")
                        .setNeutralButton("��֪����", new OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                System.exit(0);
                                
                            }
                        }).create().show();
                Looper.loop();
            }
        }.start();
    }
    
    /** 
     * ���������Ϣ���ļ��� 
    * 
     * @param ex 
     * @return  �����ļ�����,���ڽ��ļ����͵������� 
     */  
    private String saveCrashInfo2File(Throwable ex) {  
        StringBuffer sb = new StringBuffer(); 
       
        long timestamp = System.currentTimeMillis();  
        String time = formatter.format(new Date()); 
        sb.append("\n"+time+"----");
        for (Map.Entry<String, String> entry : infos.entrySet()) {  
            String key = entry.getKey();  
            String value = entry.getValue();  
            sb.append(key + "=" + value + "\n");  
        }  
  
        Writer writer = new StringWriter();  
        PrintWriter printWriter = new PrintWriter(writer);  
        ex.printStackTrace(printWriter);  
        Throwable cause = ex.getCause();  
        while (cause != null) {  
            cause.printStackTrace(printWriter);  
            cause = cause.getCause();  
        }  
        printWriter.close();  
  
        String result = writer.toString();  
        sb.append(result);  
        try {  
             
            String fileName = "exception.log";  
              
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {  
                String path = "/sdcard/crash/";  
                File dir = new File(path);  
                if (!dir.exists()) {  
                    dir.mkdirs();  
                }  
                FileOutputStream fos = new FileOutputStream(path + fileName,true);  
                fos.write(sb.toString().getBytes());  
                fos.close();  
            }  
  
            return fileName;  
        } catch (Exception e) {  
            Log.e(TAG, "an error occured while writing file...", e);  
        }  
  
        return null;  
    }  
}
