package com.coding404.roomdatabase

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.coding404.roomdatabase.databinding.RecyclerviewItemBinding

class MemberAdapter(val list: ArrayList<MemberEntity>,
                    val updateListener : (id: Int) -> Unit,
                    val deleteListener : (id: Int) -> Unit ) : RecyclerView.Adapter<MemberAdapter.MemberViewHolder>() {


    //내부클래스의 생성자에는 리사이클러뷰 아이템 xml
    class MemberViewHolder(val binding : RecyclerviewItemBinding ) : RecyclerView.ViewHolder(binding.root) {
        val row = binding.root //리사이클러뷰 최상위 뷰 (아이템)
        val data = binding.contentText
        val deleteBtn = binding.deleteBtn
        val modifyBtn = binding.modifyBtn
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemberViewHolder {
        val view : RecyclerviewItemBinding =
            RecyclerviewItemBinding.inflate( LayoutInflater.from(parent.context), parent, false )
        return MemberViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MemberViewHolder, position: Int) {

        var context = holder.itemView.context //뷰홀더의 아이템 뷰 -> 아이템의 루트뷰 -> context속성에 접근

        val item = list[position]
        holder.data.text = item.id.toString() + "." + item.name + ":" + item.email
        //아이템의 디자인 속성 지정
        if(position % 2 == 0) {
            holder.row.setBackgroundColor( ContextCompat.getColor(context , R.color.gray ) )
        } else {
            holder.row.setBackgroundColor( ContextCompat.getColor(context , R.color.white ) )
        }

        //업데이트 버튼에 이벤트
        holder.modifyBtn.setOnClickListener {
            updateListener(item.id)
        }

        //삭제 버튼에 이벤트
        holder.deleteBtn.setOnClickListener {
            deleteListener(item.id)
        }

    }




}