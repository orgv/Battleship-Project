//package com.example.battleship;
//
//import android.content.Context;
//import android.graphics.drawable.Drawable;
//import android.view.DragEvent;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//import android.widget.Toast;
//
//class MyDragListener implements View.OnDragListener {
//
//    @Override
//    public boolean onDrag(View v, DragEvent event) {
//
//        // Handles each of the expected events
//        switch (event.getAction()) {
//
//            //signal for the start of a drag and drop operation.
//            case DragEvent.ACTION_DRAG_STARTED:
//                // do nothing
//                break;
//
//            //the drag point has entered the bounding box of the View
//            case DragEvent.ACTION_DRAG_ENTERED:
//                break;
//
//            //the user has moved the drag shadow outside the bounding box of the View
//            case DragEvent.ACTION_DRAG_EXITED:
//                break;
//
//            //drag shadow has been released,the drag point is within the bounding box of the View
//            case DragEvent.ACTION_DROP:
//                // if the view is the bottomlinear, we accept the drag item
//                if(v == findViewById(R.id.bottomlinear)) {
//                    View view = (View) event.getLocalState();
//                    ViewGroup viewgroup = (ViewGroup) view.getParent();
//                    viewgroup.removeView(view);
//
//                    //change the text
//                    TextView text = (TextView) v.findViewById(R.id.text);
//                    text.setText("The item is dropped");
//
//                    LinearLayout containView = (LinearLayout) v;
//                    containView.addView(view);
//                    btn.setVisibility(View.VISIBLE);
//                    view.setVisibility(View.VISIBLE);
//                } else {
//                    View view = (View) event.getLocalState();
//                    view.setVisibility(View.VISIBLE);
//                    Context context = getApplicationContext();
//                    Toast.makeText(context, "You can't drop the image here",
//                            Toast.LENGTH_LONG).show();
//                    break;
//                }
//                break;
//
//            //the drag and drop operation has concluded.
//            case DragEvent.ACTION_DRAG_ENDED:
//                v.setBackground(normalShape);   //go back to normal shape
//
//            default:
//                break;
//        }
//        return true;
//    }
//}
//}
