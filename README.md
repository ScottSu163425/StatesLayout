# StatesLayout

Usage:  

     <com.scottsu.stateslayout.StatesLayout
        android:id="@+id/state_layout"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        app:layout_behavior="@string/appbar_scrolling_view_behavior"
        app:sl_loading_wheel_color="@color/colorPrimaryDark"
        app:sl_state_background_color="#ffffff"
        app:sl_tip_text_color="@color/textColorTertiary"
        app:sl_empty_icon="@drawable/ic_placeholder_state_empty"
        app:sl_error_icon="@drawable/ic_placeholder_state_error"
        app:sl_error_tip="error"
        app:sl_empty_tip="empty"
        app:sl_tip_text_size="12sp">

        <android.support.v7.widget.CardView
            android:layout_width="match_parent"
            android:layout_height="250dp"/>

    </com.scottsu.stateslayout.StatesLayout>
    
      ...
      
       mStatesLayout = (StatesLayout) findViewById(R.id.state_layout);
       
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
            
         mStatesLayout.showLoading();    
         mStatesLayout.showEmpty();    
         mStatesLayout.showError();
      
      
