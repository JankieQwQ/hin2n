package wang.switchy.hin2n.tool;

import android.text.TextUtils;
import android.util.Log;
import com.chad.library.adapter.base.BaseQuickAdapter;
import java.io.*;

public class IOUtils {

    public static String readTxt(String txtPath) {
        File file = new File(txtPath);
        if (file.isFile() && file.exists()) {
            try (FileInputStream fileInputStream = new FileInputStream(file);
                 InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
                 BufferedReader bufferedReader = new BufferedReader(inputStreamReader)) {
                StringBuilder stringBuilder = new StringBuilder();
                String text;
                while ((text = bufferedReader.readLine()) != null) {
                    stringBuilder.append(text);
                    stringBuilder.append("\n");
                }
                return stringBuilder.toString();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return "";
    }

    public static String readTxtLimit(String txtPath, int size) {
        File file = new File(txtPath);
        if (file.exists() && file.isFile()) {
            try (RandomAccessFile randomAccessFile = new RandomAccessFile(file, "r")) {
                long length = randomAccessFile.length();
                long start = length > size ? length - size : 0;
                randomAccessFile.seek(start);
                StringBuilder stringBuilder = new StringBuilder();
                String text;
                while ((text = randomAccessFile.readLine()) != null) {
                    stringBuilder.append(text);
                    stringBuilder.append("\n");
                }
                return stringBuilder.toString();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return "";
    }

    public static void readTxtLimits(boolean showLog, String txtPath, int size, BaseQuickAdapter mAdapter) {
        isNeedShow = showLog;
        try {
            File file = new File(txtPath);
            if (file.exists() && file.isFile()) {
                try (RandomAccessFile randomAccessFile = new RandomAccessFile(file, "r")) {
                    long length = randomAccessFile.length();
                    long start = length > size ? length - size : 0;
                    randomAccessFile.seek(start);
                    String text;
                    while (isNeedShow) {
                        text = randomAccessFile.readLine();
                        if (!TextUtils.isEmpty(text)) {
                            String finalText = text;
                            ThreadUtils.mainThreadExecutor(() -> {
                                if (mAdapter.getData().size() > 200) {
                                    mAdapter.getData().remove(0);
                                    mAdapter.notifyItemRemoved(0);
                                }
                                mAdapter.getData().add(finalText);
                                int last = mAdapter.getData().size() - 1;
                                mAdapter.notifyItemChanged(last);
                                mAdapter.getRecyclerView().scrollToPosition(last);
                            });
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean clearLogTxt(String txtPath) {
        File file = new File(txtPath);
        File fileBak = new File(txtPath + ".bak");
        if (file.exists()) {
            if (fileBak.exists()) {
                fileBak.delete();
            }
            try {
                file.renameTo(fileBak);
                return file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                return file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public static void close(Closeable closeable) {
        try {
            if (null != closeable) {
                closeable.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
