package ipvc.estg.projeto1

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import ipvc.estg.projeto1.api.EndPoints
import ipvc.estg.projeto1.api.OutputPost
import ipvc.estg.projeto1.api.ServiceBuilder
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Call
import retrofit2.Response


class Login : AppCompatActivity() {

    private var userid : Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }

    fun login(view: View) {
        val username = nome.text.toString().trim()
        val pass = password.text.toString().trim()
        val request = ServiceBuilder.buildService(EndPoints::class.java)
        val call = request.postLogin(username,pass)

        call.enqueue(object: retrofit2.Callback<OutputPost> {
            override fun onResponse(call: Call<OutputPost>, response: Response<OutputPost>){
                if (response.isSuccessful){
                    if (response.body()?.error == false){
                        val c: OutputPost = response.body()!!
                        Toast.makeText(this@Login, "Login falhado, insira as suas credenciais novamente.", Toast.LENGTH_SHORT).show()
                    } else {
                        /// GET NAME SHARED PREFERENCES ////
                        val z:OutputPost = response.body()!!
                        userid = z.id
                        var token = getSharedPreferences("nome", Context.MODE_PRIVATE)
                        var editor = token.edit()
                        editor.putString("nome_login_atual",username)
                        editor.commit()

                        var tokenid = getSharedPreferences("id", Context.MODE_PRIVATE)
                        var editorid = tokenid.edit()
                        editorid.putInt("id_login_atual",userid)
                        editorid.commit()

                        intent.putExtra("userid", userid)

                        val intent = Intent(this@Login, MainActivity::class.java)
                        Toast.makeText(this@Login, "Login realizado com sucesso" , Toast.LENGTH_SHORT).show()
                        startActivity(intent)

                    }
                }
            }
            override fun onFailure(call: Call<OutputPost>, t: Throwable){
                Toast.makeText(this@Login, "${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}