package com.example.bigprojectwhitelist

import android.content.Context
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.PackageInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ListView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_load_apps.*
import java.util.ArrayList

class LoadAppsActivity : AppCompatActivity() {

//    lateinit var listAdapter: AdapterClass
lateinit var listAdapter: WhiteListAdapterClass

    lateinit var listedApps: ArrayList<ListInfo>
    lateinit var whiteListAppsIndex: ArrayList<Int>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_load_apps)

        this.listedApps = ArrayList<ListInfo>()

        this.showWhiteListApps()
        removeBtn.setOnClickListener {
            this.removeSelectedApps()
          //  this.showWhiteListApps()

        }

    }

    fun  showWhiteListApps(){

        whiteListAppsIndex = this.getArrayList("whiteApps")

        val list = findViewById<ListView>(R.id.white_list_apps)
      //  val list = findViewById<ListView>(R.id.list_of_device_apps)

        val installedApps = getInstalledApps()
//        listAdapter = AdapterClass(this, installedApps)
        listAdapter = WhiteListAdapterClass(this, installedApps)


        list.adapter = listAdapter

    }
    fun saveArrayList(list: ArrayList<Int>, key: String) {

        val sharedPreferences = getSharedPreferences("whiteList", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val gson = Gson()
        val json = gson.toJson(list)
        editor.putString(key, json)
        editor.apply()     // This line is IMPORTANT !!!
    }

    fun removeSelectedApps(){

        val listOfApps = listAdapter.countSelectedItem()
        for (i in listOfApps){

            this.whiteListAppsIndex.removeAt(i)
            Log.e("TAG", i.toString())


        }
       this.saveArrayList(this.whiteListAppsIndex, "whiteApps")
       listAdapter.notifyDataSetChanged()



    }

    fun getArrayList(key: String): ArrayList<Int> {

    var res = ArrayList<ListInfo>()

    val sharedPreferences = getSharedPreferences("whiteList", Context.MODE_PRIVATE)
    val gson = Gson()
    val json = sharedPreferences.getString(key, null)
    val type = object : TypeToken<ArrayList<Int>>() {

    }.type
    return gson.fromJson(json, type)

    }

    private fun getInstalledApps(): ArrayList<ListInfo> {

       val listedAppsInDevice = ArrayList<ListInfo>()
        val packs = packageManager.getInstalledPackages(0)
        for (i in packs.indices) {
            val p = packs[i]
            if (isSystemPackage(p) === false) {
                val appName = p.applicationInfo.loadLabel(packageManager).toString()
                val icon = p.applicationInfo.loadIcon(packageManager)
                listedAppsInDevice.add(ListInfo(appName, icon))
            }
        }
        for (i in whiteListAppsIndex){

            listedApps.add(ListInfo(listedAppsInDevice[i].name, listedAppsInDevice[i].icon))

        }

        return listedApps
    }

    private fun isSystemPackage(pkgInfo: PackageInfo): Boolean {

        return if (pkgInfo.applicationInfo.flags and ApplicationInfo.FLAG_SYSTEM != 0) true else false
     }
   }
