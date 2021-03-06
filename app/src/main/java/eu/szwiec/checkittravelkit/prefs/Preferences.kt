package eu.szwiec.checkittravelkit.prefs

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.lifecycle.LiveData

private const val KEY_IS_FIRST_LAUNCH = "isFirstLaunch"
private const val KEY_ORIGIN = "origin"
private const val KEY_FAVORITES = "favorites"

class Preferences(context: Context, prefFileName: String) {

    private val prefs: SharedPreferences = context.getSharedPreferences(prefFileName, Context.MODE_PRIVATE)

    var isFirstLaunch: Boolean
        get() = prefs.getBoolean(KEY_IS_FIRST_LAUNCH, true)
        set(value) = prefs.edit { putBoolean(KEY_IS_FIRST_LAUNCH, value) }

    fun isFirstLaunchLD(): LiveData<Boolean> {
        return prefs.booleanLiveData(KEY_IS_FIRST_LAUNCH, true)
    }

    var origin: String
        get() = prefs.getString(KEY_ORIGIN, "")
        set(countryName) = prefs.edit { putString(KEY_ORIGIN, countryName) }

    val favorites: Set<String>
        get() = prefs.getStringSet(KEY_FAVORITES, emptySet<String>())

    fun getFavoritesLD(): LiveData<Set<String>> {
        return prefs.stringSetLiveData(KEY_FAVORITES, emptySet())
    }

    fun addFavorite(countryName: String) {
        val list = favorites.toMutableList()
        list.add(countryName)
        prefs.edit { putStringSet(KEY_FAVORITES, list.toSet()) }
    }

    fun removeFavorite(countryName: String) {
        val list = favorites.toMutableList()
        list.remove(countryName)
        prefs.edit { putStringSet(KEY_FAVORITES, list.toSet()) }
    }
}