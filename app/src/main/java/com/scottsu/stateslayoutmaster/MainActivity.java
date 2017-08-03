package com.scottsu.stateslayoutmaster;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.scottsu.stateslayout.StatesLayout;

public class MainActivity extends AppCompatActivity
{
    private Toolbar mToolbar;
    private StatesLayout mStatesLayout;
    private CardView mCardView;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setSupportActionBar(getToolbar());

        getCardView().setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Toast.makeText(MainActivity.this, "onClick", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private Toolbar getToolbar()
    {
        if (mToolbar == null)
        {
            mToolbar = (Toolbar) findViewById(R.id.toolbar_activity_main);
            mToolbar.setTitle(getString(R.string.app_name));
            mToolbar.setNavigationOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    onBackPressed();
                }
            });
        }
        return mToolbar;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        menu.add(0, 0, 0, "Loading");
        menu.add(0, 1, 1, "Empty");
        menu.add(0, 2, 2, "Error");
        menu.add(0, 3, 3, "Content");

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case 0:
                getStateView().showLoading();
                break;
            case 1:
                getStateView().showEmpty();
                break;
            case 2:
                getStateView().showError();
                break;
            case 3:
                getStateView().showContent();
                break;
        }

        return true;
    }

    private StatesLayout getStateView()
    {
        if (mStatesLayout == null)
        {
            mStatesLayout = (StatesLayout) findViewById(R.id.state_layout);
            mStatesLayout.setDefaultStateBackgroundColor(Color.GREEN);
            mStatesLayout.setDefaultEmptyIconRes(R.mipmap.ic_launcher_round);
            mStatesLayout.setDefaultTextColor(Color.WHITE);
            mStatesLayout.setErrorView(R.layout.error);

            mStatesLayout.setCallback(new StatesLayout.StatesLayoutCallback()
            {
                @Override
                public void onEmptyClick(View view)
                {
                    Toast.makeText(MainActivity.this, "onEmptyClick", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onErrorClick(View view)
                {
                    Toast.makeText(MainActivity.this, "onErrorClick", Toast.LENGTH_SHORT).show();
                }
            });
        }
        return mStatesLayout;
    }

    private CardView getCardView()
    {
        if (mCardView == null)
        {
            mCardView = (CardView)findViewById(R.id.card);
        }
        return mCardView;
    }

}
