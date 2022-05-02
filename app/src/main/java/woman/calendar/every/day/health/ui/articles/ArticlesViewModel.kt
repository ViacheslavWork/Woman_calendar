package woman.calendar.every.day.health.ui.articles

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ArticlesViewModel : ViewModel() {
    private val _tabs = MutableLiveData<ArticlesTab>()
    val tabs: LiveData<ArticlesTab> = _tabs
}