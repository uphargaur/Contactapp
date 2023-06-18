import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.example.contactconnect.History.AppDatabase
import com.example.contactconnect.Message.SendMessage.Messagedata
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SentMessageViewModel : ViewModel() {
    private val _sentMessages: MutableLiveData<List<Messagedata>> = MutableLiveData()
    val sentMessages: LiveData<List<Messagedata>>
        get() = _sentMessages

    fun loadSentMessages(context: Context) {
        viewModelScope.launch() {
            val database = Room.databaseBuilder(context, AppDatabase::class.java, "app_database").build()
            val sentMessageDao = database.sentMessageDao()
            val allSentMessages = sentMessageDao.getAllSentMessages()
            // sorting the list on the basis of timestamp
            val sortedMessages = allSentMessages.sortedBy { it.timestamp }
            println("WE are inside view model")
            _sentMessages.postValue(sortedMessages)
        }
    }
}
