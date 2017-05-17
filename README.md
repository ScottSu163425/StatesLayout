# StatesLayout

Usage:

      <com.scottsu.stateslayout.StatesLayout
        android:id="@+id/state_layout"
        android:layout_width="match_parent"
        android:layout_height="match_parent" 
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
