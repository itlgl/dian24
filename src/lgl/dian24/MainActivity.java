package lgl.dian24;

import java.util.HashSet;
import java.util.Set;

import lgl.dian24.Dian24.Num;
import android.app.Activity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements View.OnClickListener {
	TextView tv_num1, tv_num2, tv_num3, tv_num4;
	TextView tv_result;
	View btn_random, btn_ok;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		setTitle("24点");
		tv_num1 = (TextView) findViewById(R.id.tv_num1);
		tv_num2 = (TextView) findViewById(R.id.tv_num2);
		tv_num3 = (TextView) findViewById(R.id.tv_num3);
		tv_num4 = (TextView) findViewById(R.id.tv_num4);
		tv_result = (TextView) findViewById(R.id.tv_result);
		tv_result.setMovementMethod(ScrollingMovementMethod.getInstance());
		btn_ok = findViewById(R.id.btn_ok);
		btn_ok.setOnClickListener(this);
		btn_random = findViewById(R.id.btn_random);
		btn_random.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_random:
			tv_num1.setText(String.valueOf((int) (Math.random() * 23 + 1)));
			tv_num2.setText(String.valueOf((int) (Math.random() * 23 + 1)));
			tv_num3.setText(String.valueOf((int) (Math.random() * 23 + 1)));
			tv_num4.setText(String.valueOf((int) (Math.random() * 23 + 1)));
			break;
		case R.id.btn_ok:
			try {
				Num[] nums = new Num[4];
				float number = Float.parseFloat(tv_num1.getText().toString());
				nums[0] = new Num(number);
				number = Float.parseFloat(tv_num2.getText().toString());
				nums[1] = new Num(number);
				number = Float.parseFloat(tv_num3.getText().toString());
				nums[2] = new Num(number);
				number = Float.parseFloat(tv_num4.getText().toString());
				nums[3] = new Num(number);
				Set<String> resultList = new HashSet<String>();
				Dian24.dian24(nums, resultList);
				tv_result.setText("");
				tv_result.append((int) nums[0].number + " ");
				tv_result.append((int) nums[1].number + " ");
				tv_result.append((int) nums[2].number + " ");
				tv_result.append((int) nums[3].number + "结果：\n");
				if(resultList.size() == 0) {
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
