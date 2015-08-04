package com.example.jeanpaucar.litorobotapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jeanpaucar.litorobotapp.R;
import com.example.jeanpaucar.litorobotapp.model.PiecesItem;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by JeanPaucar on 04/08/2015.
 */
public class PiecesAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<PiecesItem> piecesItems;
    private LayoutInflater layoutInflater;

    public PiecesAdapter(Context context, ArrayList<PiecesItem> piecesItems) {
        this.context = context;
        this.piecesItems = piecesItems;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return piecesItems.size();
    }

    @Override
    public Object getItem(int position) {
        return piecesItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Holder holder = null;
        PiecesItem piecesItem = piecesItems.get(position);

        if(convertView == null){

            holder = new Holder();
            convertView = layoutInflater.inflate(R.layout.item_pieces,parent,false);

            holder.pieceName = (TextView)convertView.findViewById(R.id.textViewName);
            holder.pieceImage =(ImageView)convertView.findViewById(R.id.imageButtonImage);
            convertView.setTag(holder);
        }
        else{
            holder = (Holder)convertView.getTag();
        }

        holder.pieceName.setText(piecesItem.getPieceName());
        holder.pieceImage.setImageResource(piecesItem.getPieceImage());

        return convertView;
    }

    private class Holder{
        public TextView pieceName;
        public ImageView pieceImage;
    }
}
