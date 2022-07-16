//package com.example.battleship;
//
//import android.content.ClipData;
//import android.view.MotionEvent;
//import android.view.View;
//import android.widget.ImageView;
//
//public final class ChoiceTouchListener implements View.OnTouchListener {
//
//    @Override
//    public boolean onTouch(View view, MotionEvent motionEvent) {
//        final int X = (int) motionEvent.getRawX();
//        final int Y = (int) motionEvent.getRawY();
//        switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
//            case MotionEvent.ACTION_DOWN:
//                break;
//            case MotionEvent.ACTION_UP:
//                break;
//            case MotionEvent.ACTION_POINTER_DOWN:
//                break;
//            case MotionEvent.ACTION_POINTER_UP:
//                break;
//            case MotionEvent.ACTION_MOVE:
//                view.setX(X - view.getWidth() / 2);
//                view.setY(Y - view.getHeight());
//                break;
//        }
//        return true;
//    }
//
//
//
//}
////        if ((motionEvent.getAction() == MotionEvent.ACTION_DOWN) && ((ImageView) view).getDrawable() != null){
////            ClipData data = ClipData.newPlainText("","");
////            View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
////            view.startDragAndDrop(data,shadowBuilder,null,0);
////            return true;
////        }
