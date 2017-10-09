package com.example.rako.bankingapp.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.rako.bankingapp.R;

/**
 * Created by rako on 29/09/2017.
 */

public class FragmentRecipeStepDetail extends Fragment implements View.OnTouchListener{
    private TextView titulo;
    private TextView description;
    private String urlVideo;
    private String stringTitulo;
    private String stringDescription;
    private String TAG = this.getTag();
    private ImageButton buttonRight;
    private ImageButton buttonLeft;


    private GestureDetector gestureDetector;
    private InterfacePhone listenerSwipe;

    public interface InterfacePhone {
        void delegateSwipeRight();
        void delegateSwipeLeft();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.listenerSwipe = (InterfacePhone) context;
        this.gestureDetector = new GestureDetector(context, new GestureListener());
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_step_detail_view, container, false);
        titulo = view.findViewById(R.id.titulo_step);
        description = view.findViewById(R.id.description);

        buttonRight = view.findViewById(R.id.button_right);
        buttonLeft = view.findViewById(R.id.button_left);

        buttonRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listenerSwipe.delegateSwipeRight();
            }
        });

        buttonLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listenerSwipe.delegateSwipeLeft();
            }
        });

        titulo.setText(stringTitulo);
        description.setText(stringDescription);

        view.setOnTouchListener(this);
        return view;
    }

    public void setDescription(String descriptionString) {
        this.stringDescription = descriptionString;
    }

    public void setTitulo(String tituloString) {
        this.stringTitulo = tituloString;
    }

    public void setUrlVideo(String url) {
        this.urlVideo = url;
    }



    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {

        return gestureDetector.onTouchEvent(motionEvent);
    }


    private final class GestureListener extends GestureDetector.SimpleOnGestureListener {

        private static final int SWIPE_THRESHOLD = 100;
        private static final int SWIPE_VELOCITY_THRESHOLD = 100;

        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            boolean result = false;
            try {
                float diffY = e2.getY() - e1.getY();
                float diffX = e2.getX() - e1.getX();
                if (Math.abs(diffX) > Math.abs(diffY)) {
                    if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                        if (diffX > 0) {
                            listenerSwipe.delegateSwipeRight();
                        } else {
                            listenerSwipe.delegateSwipeLeft();
                        }
                        result = true;
                    }
                }

            } catch (Exception exception) {
                exception.printStackTrace();
            }
            return result;
        }
    }

}
