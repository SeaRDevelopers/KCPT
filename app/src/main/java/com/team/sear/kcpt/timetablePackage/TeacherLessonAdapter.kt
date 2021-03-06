package com.team.sear.kcpt.timetablePackage

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.team.sear.kcpt.R

class TeacherLessonAdapter(private val lessons: ArrayList<Lesson?>) : RecyclerView.Adapter<TeacherLessonAdapter.ViewHolder>() {

    private lateinit var itemView: View

    override fun getItemCount() = lessons.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_timetable, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, p: Int) {
        try{
            holder.lessonNum!!.text = lessons[p]!!.lessonNum
            holder.lesson!!.text = lessons[p]!!.lesson
            holder.roomNum!!.text = lessons[p]!!.roomNum
            holder.groupName!!.text = lessons[p]!!.groupName
            holder.lessonTime!!.text = lessons[p]!!.lessonTime
        } catch (e: Exception) {
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var lessonNum: TextView? = null
        var lesson: TextView? = null
        var roomNum: TextView? = null
        var groupName: TextView? = null
        var lessonTime: TextView? = null

        init {
            lessonNum = itemView.findViewById(R.id.lessonNumTV)
            lesson = itemView.findViewById(R.id.lessonTV)
            roomNum = itemView.findViewById(R.id.roomNumTV)
            groupName = itemView.findViewById(R.id.anybodyNameTV)
            lessonTime = itemView.findViewById(R.id.lessonTimeTV)
        }
    }
}