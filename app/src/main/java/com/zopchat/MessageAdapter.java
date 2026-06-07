package com.zopchat;

import android.view.*; import android.widget.*; import androidx.annotation.NonNull; import androidx.recyclerview.widget.RecyclerView; import java.util.*;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.VH>{
    List<MessageRow> rows=new ArrayList<>(); String myUid; public MessageAdapter(String uid){myUid=uid;} public void setRows(List<MessageRow> r){rows=r; notifyDataSetChanged();}
    @NonNull public VH onCreateViewHolder(@NonNull ViewGroup p,int v){return new VH(LayoutInflater.from(p.getContext()).inflate(R.layout.item_message,p,false));}
    public void onBindViewHolder(@NonNull VH h,int i){MessageRow m=rows.get(i); boolean mine=myUid.equals(m.senderId); h.msg.setText(m.text==null?"":m.text); h.time.setText(Utils.timeShort(m.createdAt)); LinearLayout.LayoutParams lp=(LinearLayout.LayoutParams)h.msg.getLayoutParams(); lp.gravity= mine?Gravity.END:Gravity.START; h.msg.setLayoutParams(lp); LinearLayout.LayoutParams tlp=(LinearLayout.LayoutParams)h.time.getLayoutParams(); tlp.gravity=mine?Gravity.END:Gravity.START; h.time.setLayoutParams(tlp); h.msg.setBackgroundResource(mine?R.drawable.bg_msg_me:R.drawable.bg_msg_other);}
    public int getItemCount(){return rows.size();}
    static class VH extends RecyclerView.ViewHolder{TextView msg,time; VH(View v){super(v); msg=v.findViewById(R.id.msgTv); time=v.findViewById(R.id.timeTv);}}
}
