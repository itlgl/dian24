package lgl.dian24;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

import lgl.dian24.Dian24.Num;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.ShareActionProvider;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewConfiguration;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Poker24Activity extends ActionBarActivity implements
		OnClickListener {
	private ImageView iv_1, iv_2, iv_3, iv_4;
	private TextView tv_result;
	private View btn_random, btn_ok;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_24);
		setOverflowShowingAlways();
		findView();
		initView();

		onClick(btn_random);
	}

	private void setOverflowShowingAlways() {
		try {
			ViewConfiguration config = ViewConfiguration.get(this);
			Field menuKeyField = ViewConfiguration.class
					.getDeclaredField("sHasPermanentMenuKey");
			menuKeyField.setAccessible(true);
			menuKeyField.setBoolean(config, false);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater menuInflater = getMenuInflater();
		menuInflater.inflate(R.menu.poker24_menu, menu);
		MenuItem shareItem = menu.findItem(R.id.action_share);
		ShareActionProvider shareActionProvider = (ShareActionProvider) MenuItemCompat
				.getActionProvider(shareItem);
		shareActionProvider.setShareIntent(getShareIntent());
		return super.onCreateOptionsMenu(menu);
	}

	private Intent getShareIntent() {
		Intent intent = new Intent(Intent.ACTION_SEND);
		intent.setType("text/*");
		return intent;
	}

	@Override
	public boolean onMenuOpened(int featureId, Menu menu) {
		if (featureId == Window.FEATURE_ACTION_BAR && menu != null) {
			if (menu.getClass().getSimpleName().equals("MenuBuilder")) {
				try {
					Method m = menu.getClass().getDeclaredMethod(
							"setOptionalIconsVisible", Boolean.TYPE);
					m.setAccessible(true);
					m.invoke(menu, true);
				} catch (Exception e) {
				}
			}
		}
		return super.onMenuOpened(featureId, menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_to_main:
			startActivity(new Intent(this, MainActivity.class));
			return true;

		}
		return super.onOptionsItemSelected(item);
	}

	private void findView() {
		iv_1 = (ImageView) findViewById(R.id.iv_1);
		iv_2 = (ImageView) findViewById(R.id.iv_2);
		iv_3 = (ImageView) findViewById(R.id.iv_3);
		iv_4 = (ImageView) findViewById(R.id.iv_4);

		tv_result = (TextView) findViewById(R.id.tv_result);
		tv_result.setMovementMethod(ScrollingMovementMethod.getInstance());
		btn_ok = findViewById(R.id.btn_ok);
		btn_random = findViewById(R.id.btn_random);
	}

	private void initView() {
		iv_1.setOnClickListener(this);
		iv_2.setOnClickListener(this);
		iv_3.setOnClickListener(this);
		iv_4.setOnClickListener(this);

		btn_ok.setOnClickListener(this);
		btn_random.setOnClickListener(this);
	}

	private void setImageNumber(ImageView img, int number) {
		int pokerColor = (int) (Math.random() * 3 + 1);
		String imgRes = "poker_" + Integer.toHexString(number) + pokerColor;
		int imgID = getResources().getIdentifier(imgRes, "drawable",
				getPackageName());
		img.setImageResource(imgID);
		img.setTag(number);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_1:

			break;
		case R.id.iv_2:

			break;
		case R.id.iv_3:

			break;
		case R.id.iv_4:

			break;
		case R.id.btn_random:
			setImageNumber(iv_1, (int) (Math.random() * 12 + 1));
			setImageNumber(iv_2, (int) (Math.random() * 12 + 1));
			setImageNumber(iv_3, (int) (Math.random() * 12 + 1));
			setImageNumber(iv_4, (int) (Math.random() * 12 + 1));
			break;
		case R.id.btn_ok:
			try {
				Num[] nums = new Num[4];
				nums[0] = new Num((int) iv_1.getTag());
				nums[1] = new Num((int) iv_2.getTag());
				nums[2] = new Num((int) iv_3.getTag());
				nums[3] = new Num((int) iv_4.getTag());
				Set<String> resultList = new HashSet<String>();
				Dian24.dian24(nums, resultList);
				tv_result.setText("");
				tv_result.append((int) nums[0].number + " ");
				tv_result.append((int) nums[1].number + " ");
				tv_result.append((int) nums[2].number + " ");
				tv_result.append((int) nums[3].number + "结果：\n");
				if (resultList.size() == 0) {
					tv_result.append("不能得到24，不信你试试看");
				} else {
					for (String result : resultList) {
						tv_result.append(result);
						tv_result.append("\n");
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				Toast.makeText(this, "不要逗我好吗", Toast.LENGTH_LONG).show();
			}
			break;
		}
	}
}
