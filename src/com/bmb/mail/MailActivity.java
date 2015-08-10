package com.bmb.mail;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipData.Item;
import android.content.ClipboardManager;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Toast;

import com.bmb.bean.MultiMailSenderInfo;
import com.bmb.bean.MultiMailsender;

/**
 * @author blackman8
 */
public class MailActivity extends Activity implements OnClickListener{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mail);

		initMailSite();
		initEmailContent();
		findViewById(R.id.send).setOnClickListener(this);
		initCls();
	}

	private void initCls() {
		findViewById(R.id.clear).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		if(v.getId() == R.id.clear){
			((EditText) findViewById(R.id.email_content)).setText("");
		}else if(v.getId() == R.id.send){
			EditText email_content = (EditText) findViewById(R.id.email_content);
			String mail_content = email_content.getText().toString();

			EditText mail_site = (EditText) findViewById(R.id.et_mail_site);
			String site = mail_site.getText().toString();

			if(TextUtils.isEmpty(mail_content) || TextUtils.isEmpty(site)){
				showToast("invaild mail site or content!");
				return;
			}

			sendMail(site, mail_content, null);
		}
	}

	private void initMailSite() {
		EditText mail_site = (EditText) findViewById(R.id.et_mail_site);
		mail_site.setText("default_send_email_address");
	}

	private void initEmailContent() {
		String text = getClipboardContent();
		if(TextUtils.isEmpty(text)){
			return;
		}
		EditText email_content = (EditText) findViewById(R.id.email_content);
		email_content.setText(text);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	/**
	 * 获取剪贴板内容
	 * @return
	 */
	private String getClipboardContent(){
		ClipboardManager manager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
		ClipData mData = manager.getPrimaryClip();
		if(null != mData && mData.getItemCount() > 0){
			Item item = mData.getItemAt(0);
			return item.getText().toString();
		}
		return "";
	}

	private void sendMail(String site, String text, Bitmap bitmap){
		MultiMailSenderInfo info = new MultiMailSenderInfo();

		info.setUserName("email_address");
		info.setPassword("password");
		info.setMailServerPort("25");
		info.setValidate(true);
		info.setMailServerHost("smtp.163.com");
		info.setFromAddress("email_address");
		info.setToAddress(site);
		if(text.length() < 6){
			info.setSubject(text);
		}else{
			info.setSubject(text.subSequence(0, 6).toString());
		}
		info.setContent(text);

		new MyAsyncTask().execute(info);
	}

	class MyAsyncTask extends AsyncTask<MultiMailSenderInfo, Integer, String>{

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			findViewById(R.id.pBar).setVisibility(View.VISIBLE);
			findViewById(R.id.send).setVisibility(View.GONE);
			findViewById(R.id.send).setClickable(false);
		}

		@Override
		protected String doInBackground(MultiMailSenderInfo... params) {
			if(params == null){
				return "0";//参数错误
			}
			MultiMailSenderInfo info = params[0];
			try{
				if(MultiMailsender.sendTextMail(info)){
					return "1";
				}else{
					return "0";
				}
			}catch(Exception e){
				return "2";
			}
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			findViewById(R.id.send).setClickable(true);
			findViewById(R.id.pBar).setVisibility(View.GONE);
			findViewById(R.id.send).setVisibility(View.VISIBLE);
			if(result.equals("1")){
				showToast("Email sent successfully.");
			}else if(result.equals("0")){
				showToast("Email was sent failed.");
			}else{
				showToast("send Email error!");
			}
		}

	}

	private void showToast(String str){
		if(TextUtils.isEmpty(str))
			return;
		Toast.makeText(MailActivity.this, str, Toast.LENGTH_LONG).show();
	}
}
