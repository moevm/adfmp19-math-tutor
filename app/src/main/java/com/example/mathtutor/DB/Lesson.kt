package com.example.mathtutor.DB

import android.os.Parcel
import android.os.Parcelable

class Lesson(val topic: String, val content: Int, val last_visited: Long?, val id: Long) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readInt(),
        parcel.readValue(Long::class.java.classLoader) as? Long,
        parcel.readLong()
    )

    override fun toString(): String {
        return "topic: ${this.topic} content: ${this.content} last_visited: ${this.last_visited} id: ${this.id}"
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(topic)
        parcel.writeInt(content)
        parcel.writeValue(last_visited)
        parcel.writeLong(id)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Lesson> {
        override fun createFromParcel(parcel: Parcel): Lesson {
            return Lesson(parcel)
        }

        override fun newArray(size: Int): Array<Lesson?> {
            return arrayOfNulls(size)
        }
    }
}