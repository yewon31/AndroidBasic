package com.coding404.recyclerview


object TodoList { //싱글톤

    fun getTodoList() : ArrayList<Todo> {

        var result = ArrayList<Todo>()
        result.add( Todo("일어나", "오전 7:00") )
        result.add( Todo("아침먹기", "오전 8:00") )
        result.add( Todo("출근하기", "오전 9:00") )
        result.add( Todo("안드로이드 하기", "오전 10:00") )
        result.add( Todo("스트레스받기", "오전 11:00") )
        result.add( Todo("에러 해결하기", "오후 12:00") )
        result.add( Todo("점심먹으면서 머리속으로 생각하기", "오후 13:00") )
        result.add( Todo("문득 해결할 방법이 생각남", "오후 14:00") )
        result.add( Todo("적용하기", "오후 15:00") )
        result.add( Todo("fail", "오후 16:00") )
        result.add( Todo("집에갈 생각중", "오후 17:00") )
        result.add( Todo("야근", "오후 18:00") )
        result.add( Todo("에러 해결하기", "오후 19:00") )
        result.add( Todo("퇴근..", "오후 20:00") )

        return result
    }
}