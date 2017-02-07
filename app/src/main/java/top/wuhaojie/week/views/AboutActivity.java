package top.wuhaojie.week.views;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;

import butterknife.BindView;
import butterknife.OnClick;
import top.wuhaojie.week.BaseActivity;
import top.wuhaojie.week.R;

public class AboutActivity extends BaseActivity {

    @BindView(R.id.open_network)
    Button mOpenNetwork;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_about;
    }

    @OnClick(R.id.open_network)
    public void onClick() {
        Uri uri = Uri.parse("https://github.com/a-voyager/WeekToDo");
        Intent it = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(it);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
