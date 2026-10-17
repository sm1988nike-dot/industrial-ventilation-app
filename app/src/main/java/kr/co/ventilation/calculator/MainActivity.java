package kr.co.ventilation.calculator;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.WebSettings;
import android.webkit.JavascriptInterface;
import android.content.Intent;
import android.print.PrintAttributes;
import android.print.PrintManager;
import android.content.Context;
import android.net.Uri;
import android.widget.Toast;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class MainActivity extends Activity {
    private WebView webView;
    private String pendingCsv = null;
    private static final int CREATE_CSV = 101;

    @Override public void onCreate(Bundle state) {
        super.onCreate(state);
        webView = new WebView(this);
        webView.setWebViewClient(new WebViewClient());
        webView.setWebChromeClient(new WebChromeClient());
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setAllowFileAccess(true);
        settings.setAllowFileAccessFromFileURLs(false);
        settings.setAllowUniversalAccessFromFileURLs(false);
        webView.addJavascriptInterface(new AppBridge(), "AndroidApp");
        setContentView(webView);
        webView.loadUrl("file:///android_asset/index.html");
    }

    public class AppBridge {
        @JavascriptInterface public void saveCsv(String csv) {
            runOnUiThread(() -> {
                pendingCsv = csv;
                Intent intent = new Intent(Intent.ACTION_CREATE_DOCUMENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("text/csv");
                intent.putExtra(Intent.EXTRA_TITLE, "후드_필요환기량_계산.csv");
                startActivityForResult(intent, CREATE_CSV);
            });
        }

        @JavascriptInterface public void printPage() {
            runOnUiThread(() -> {
                PrintManager manager = (PrintManager) getSystemService(Context.PRINT_SERVICE);
                manager.print("후드 필요환기량", webView.createPrintDocumentAdapter("후드 필요환기량"), new PrintAttributes.Builder().build());
            });
        }
    }

    @Override protected void onActivityResult(int request, int result, Intent data) {
        super.onActivityResult(request, result, data);
        if (request == CREATE_CSV && result == RESULT_OK && data != null && pendingCsv != null) {
            try (OutputStream stream = getContentResolver().openOutputStream(data.getData())) {
                if (stream != null) {
                    stream.write(pendingCsv.getBytes(StandardCharsets.UTF_8));
                    Toast.makeText(this, "CSV 저장 완료", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                Toast.makeText(this, "저장 실패: " + e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
        pendingCsv = null;
    }

    @Override public void onBackPressed() {
        if (webView.canGoBack()) webView.goBack(); else super.onBackPressed();
    }

    @Override protected void onDestroy() {
        webView.destroy();
        super.onDestroy();
    }
}
