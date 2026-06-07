package com.zopchat;

import android.view.*; import android.widget.*; import androidx.annotation.NonNull; import androidx.recyclerview.widget.RecyclerView; import java.util.*;

public class ChatListAdapter extends RecyclerView.Adapter<ChatListAdapter.VH>{
    public interface OnClick{void open(ChatRow row);} List<ChatRow> rows=new ArrayList<>(); OnClick cb;
    public ChatListAdapter(OnClick c){cb=c;} public void setRows(List<ChatRow> r){rows=r; notifyDataSetChanged();}
    @NonNull public VH onCreateViewHolder(@NonNull ViewGroup p,int v){return new VH(LayoutInflater.from(p.getContext()).inflate(R.layout.item_chat,p,false));}
    public void onBindViewHolder(@NonNull VH h,int i){ChatRow r=rows.get(i); h.avatar.setText(Utils.firstLetter(r.name)); h.name.setText(r.name==null||r.name.isEmpty()?r.mobile:r.name); h.last.setText(r.lastMessage==null||r.lastMessage.isEmpty()?"Tap to chat":r.lastMessage); h.time.setText(Utils.timeShort(r.time)); h.itemView.setOnClickListener(v->cb.open(r));}
    public int getItemCount(){return rows.size();}
    static class VH extends RecyclerView.ViewHolder{TextView avatar,name,last,time; VH(View v){super(v); avatar=v.findViewById(R.id.avatarTv); name=v.findViewById(R.id.nameTv); last=v.findViewById(R.id.lastTv); time=v.findViewById(R.id.timeTv);}}
}
