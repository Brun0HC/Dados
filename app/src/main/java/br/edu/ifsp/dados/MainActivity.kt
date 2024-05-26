package br.edu.ifsp.dados
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import br.edu.ifsp.dados.databinding.ActivityMainBinding
import kotlin.random.Random
import kotlin.random.nextInt

class MainActivity : AppCompatActivity() {
    private lateinit var activityMainBinding: ActivityMainBinding
    private lateinit var geradorRandomico: Random
    private lateinit var configuracao: Configuracao

    private lateinit var settingActivityLauncher: ActivityResultLauncher<Intent>
        override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

            //setando config
        configuracao = Configuracao(1, 6)

        geradorRandomico = Random(System.currentTimeMillis())

        activityMainBinding.jogarDadosBt.setOnClickListener {
            val resultado: Int = geradorRandomico.nextInt(1..configuracao.numeroLados)
            //fazendo o game com 2 dados
            if (configuracao.numeroDados == 2) {
                activityMainBinding.resultadoIv.visibility = View.VISIBLE
                activityMainBinding.resultadoIv2.visibility = View.VISIBLE
                val resultado2: Int = geradorRandomico.nextInt(1..configuracao.numeroLados)
                "Os dados cairam com os lados $resultado e $resultado2".also {
                    activityMainBinding.resultadoTV.text = it
                }
                if (configuracao.numeroLados <= 6) {

                    val nomeImagem1: String = "dice_${resultado}"
                    activityMainBinding.resultadoIv.setImageResource(
                        resources.getIdentifier(nomeImagem1, "mipmap", packageName)
                    )
                    val nomeImagem2: String = "dice_${resultado2}"
                    activityMainBinding.resultadoIv2.setImageResource(
                        resources.getIdentifier(nomeImagem2, "mipmap", packageName)
                    )
                } else {
                    activityMainBinding.resultadoIv.visibility = View.GONE
                    activityMainBinding.resultadoIv2.visibility = View.GONE
                }
            } else {
                //game com 1 dado
                "O dado caiu com o lado $resultado".also {
                    activityMainBinding.resultadoTV.text = it
                }
                if (configuracao.numeroLados <= 6) {
                    activityMainBinding.resultadoIv.visibility = View.VISIBLE
                    activityMainBinding.resultadoIv2.visibility = View.GONE
                    val nomeImagem: String = "dice_${resultado}"
                    activityMainBinding.resultadoIv.setImageResource(
                        resources.getIdentifier(nomeImagem, "mipmap", packageName)
                    )
                } else {
                    activityMainBinding.resultadoIv.visibility = View.GONE
                    activityMainBinding.resultadoIv2.visibility = View.GONE
                }
            }

        }
        //callback para receber result da config
        settingActivityLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            result ->
            if (result.resultCode == RESULT_OK) {
                if (result.data != null) {
                    val config = result.data?.getParcelableExtra<Configuracao>(Intent.EXTRA_USER)

                    if (config != null) {
                        configuracao = config
                    }
                }

            }
        }
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.settingMi) {
            val settingsIntent = Intent(this, SettingsActivity::class.java)
            settingActivityLauncher.launch(settingsIntent)
            return true
        }
        return false
    }
}