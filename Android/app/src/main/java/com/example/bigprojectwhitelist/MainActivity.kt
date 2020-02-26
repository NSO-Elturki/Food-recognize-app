package com.example.bigprojectwhitelist

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.pm.ApplicationInfo
import android.content.pm.PackageInfo
import android.util.Log
import android.widget.ListView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_main.*
import java.util.ArrayList
import android.content.Intent
import androidx.core.app.ComponentActivity
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T



class MainActivity : AppCompatActivity() {

    lateinit var listAdapter: AdapterClass



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val list = findViewById<ListView>(R.id.list_of_device_apps)
        val installedApps = getInstalledApps()
//       val listAdapter = AdapterClass(this, installedApps)
        listAdapter = AdapterClass(this, installedApps)

        list.adapter = listAdapter

        saveBtn.setOnClickListener {

            this.saveSelectedApps()
        }
        goToLoadPageBtn.setOnClickListener {

            val i = Intent(baseContext, LoadAppsActivity::class.java)
            startActivity(i)
        }

    }

    private fun getInstalledApps(): ArrayList<ListInfo> {
        val res = ArrayList<ListInfo>()
        val packs = packageManager.getInstalledPackages(0)
        for (i in packs.indices) {
            val p = packs[i]
            if (isSystemPackage(p) === false) {
                val appName = p.applicationInfo.loadLabel(packageManager).toString()
                val icon = p.applicationInfo.loadIcon(packageManager)
                res.add(ListInfo(appName, icon))
            }
        }
        return res
    }

//    private fun saveSelectedApps(){
//
//        val allApps = getInstalledApps()
//        val checkedApps = listAdapter.countSelectedItem()
//       // val whiteListApps = ArrayList<ListInfo>()
//        val whiteListAppsNames = ArrayList<String>()
//
//        for (i in checkedApps){
//
//            whiteListAppsNames.add(allApps[i].name)
//
//        }
//
//        this.saveArrayList(whiteListAppsNames, "whiteApps")
//        Log.e("SAVE", "data saved")
//
//
//
//
//    }
    private fun saveSelectedApps(){

    val checkedApps = listAdapter.countSelectedItem()

    this.saveArrayList(checkedApps, "whiteApps")
    Log.e("SAVE", "data saved")
    for (i in checkedApps){

        Log.e("check", i.toString())

    }

    }
    fun saveArrayList(list: ArrayList<Int>, key: String) {

        val sharedPreferences = getSharedPreferences("whiteList", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val gson = Gson()
        val json = gson.toJson(list)
        editor.putString(key, json)
        editor.apply()     // This line is IMPORTANT !!!
    }

//    fun saveArrayList(list: ArrayList<Int>, key: String) {
//
//        val sharedPreferences = getSharedPreferences("whiteList", Context.MODE_PRIVATE)
//        val editor = sharedPreferences.edit()
//        val gson = Gson()
//        val json = gson.toJson(list)
//        editor.putString(key, json)
//        editor.apply()     // This line is IMPORTANT !!!
//    }

//    fun saveArrayList(list: ArrayList<String>, key: String) {
//
//        val sharedPreferences = getSharedPreferences("whiteList", Context.MODE_PRIVATE)
//        val editor = sharedPreferences.edit()
//        val gson = Gson()
//        val json = gson.toJson(list)
//        editor.putString(key, json)
//        editor.apply()     // This line is IMPORTANT !!!
//    }


    private fun isSystemPackage(pkgInfo: PackageInfo): Boolean {
        return if (pkgInfo.applicationInfo.flags and ApplicationInfo.FLAG_SYSTEM != 0) true else false
    }

}
