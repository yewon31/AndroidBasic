package com.coding404.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.coding404.fragment.databinding.FragmentFirstBinding

class FirstFragment : Fragment() {

    //Fragment를 상속받고, OnCreateView를 오버라이딩 해주면 됩니다.

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //return inflater.inflate(R.layout.fragment_first, container, false)
        val binding = FragmentFirstBinding.inflate(layoutInflater, container, false)


        binding.nextBtn.setOnClickListener {
            
            //상위 프래그먼트로 접근해서 화면을 교환
//            parentFragmentManager.beginTransaction()
//                .replace(R.id.main_fragment, OtherFragment() )
//                .commit()

            //프래그먼트에서 activity의 기능을 사용해야 할 때가 있습니다.
            //나를 포함하고있는 상위 엑티비티를 가르킴
            requireActivity().supportFragmentManager.beginTransaction()
                //1st
                //.replace(R.id.main_fragment, OtherFragment())

                //2nd
                //.add(R.id.main_fragment, OtherFragment() ).hide(this) //만약에 hide로 프래그먼트를 숨겼다면, 이 프래그먼트가 보여지는 순간에 show()로 다시 보여지게 만들 수 있음.

                //3nd
                .add(R.id.main_fragment, OtherFragment() ).remove(this) //replace와 같은표현
                
                .addToBackStack(null) //뒤로가기 처리시 - 현재 프래그먼트(first) stack기록한다. 뒤로가기 클릭시에, stack에 남아있는 프래그먼트를 보여주게 합니다.
                //사용자가 뒤로가기를 클릭하지 않게 되면, 백스택에 쌓이기때문에, 적절한 처리가 필요함
                .commit()

        }


        binding.nextAcBtn.setOnClickListener {
            val intent = Intent( context , OtherActivity::class.java )
            startActivity(intent)
        }





        return binding.root
    }

}