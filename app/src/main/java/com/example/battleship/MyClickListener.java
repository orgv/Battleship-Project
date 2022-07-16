//package com.example.battleship;
//
//import android.content.ClipData;
//import android.content.ClipDescription;
//import android.view.MotionEvent;
//import android.view.View;
//
//public final class MyClickListener implements View.OnTouchListener {
//
//
//    @Override
//    public boolean onTouch(View view, MotionEvent motionEvent) {
//        ClipData.Item item = new ClipData.Item((CharSequence)view.getTag());
//
//        String[] mimeTypes = { ClipDescription.MIMETYPE_TEXT_PLAIN };
//        ClipData data = new ClipData(view.getTag().toString(), mimeTypes, item);
//        View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
//
//        view.startDragAndDrop( data, //data to be dragged
//                shadowBuilder, //drag shadow
//                view, //local data about the drag and drop operation
//                0   //no needed flags
//        );
//
//
//        view.setVisibility(View.INVISIBLE);
//        return true;
//    }
//
//}
