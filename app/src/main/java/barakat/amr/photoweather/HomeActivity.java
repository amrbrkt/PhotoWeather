package barakat.amr.photoweather;

import barakat.amr.photoweather.base.BaseActivity;

public class HomeActivity extends BaseActivity {

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_home;
    }

    @Override
    protected int getActivityTitle() {
        return R.string.app_name;
    }

    @Override
    protected void afterActivityInflation() {

    }
}
