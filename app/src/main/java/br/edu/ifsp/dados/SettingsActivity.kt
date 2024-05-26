package br.edu.ifsp.dados
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import br.edu.ifsp.dados.SettingsActivity.Constantes.CONFIGURACOES_ARQUIVO
import br.edu.ifsp.dados.SettingsActivity.Constantes.NUMERO_DADOS_ATRIBUTO
import br.edu.ifsp.dados.SettingsActivity.Constantes.NUMERO_LADOS_ATRIBUTO
import br.edu.ifsp.dados.SettingsActivity.Constantes.VALOR_NAO_ENCONTRADO
import br.edu.ifsp.dados.databinding.ActivitySettingsBinding

class SettingsActivity : AppCompatActivity() {
    private lateinit var activitySettingsBinding: ActivitySettingsBinding
    private object Constantes {
        val CONFIGURACOES_ARQUIVO = "configuracoes"
        val NUMERO_DADOS_ATRIBUTO = "numeroDados"
        val NUMERO_LADOS_ATRIBUTO = "numeroLados"
        val VALOR_NAO_ENCONTRADO = -1
    }
    private lateinit var configuracoesSharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activitySettingsBinding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(activitySettingsBinding.root)

        configuracoesSharedPreferences = getSharedPreferences(CONFIGURACOES_ARQUIVO, MODE_PRIVATE)
        carregaConfiguracoes()

        activitySettingsBinding.salvarBt.setOnClickListener {
            val numeroDado: Int = (activitySettingsBinding.numeroDadosSp.selectedView as TextView).text.toString().toInt()
            val numeroLados: Int = activitySettingsBinding.numeroLadosEt.text.toString().toInt()
            val configuracao = Configuracao(numeroDado, numeroLados)
            val retornoIntent = Intent()
            retornoIntent.putExtra(Intent.EXTRA_USER, configuracao)
            setResult(RESULT_OK, retornoIntent)

            salvaConfiguracoes(numeroDado, numeroLados)
            finish()
        }
    }

    //carrega as configs salvas
    private fun carregaConfiguracoes() {
        val numeroDados: Int = configuracoesSharedPreferences.getInt(NUMERO_DADOS_ATRIBUTO, VALOR_NAO_ENCONTRADO)
        val numeroLados: Int = configuracoesSharedPreferences.getInt(NUMERO_LADOS_ATRIBUTO, VALOR_NAO_ENCONTRADO)
        if (numeroDados != VALOR_NAO_ENCONTRADO){
            activitySettingsBinding.numeroDadosSp.setSelection(numeroDados - 1)
        }
        if (numeroLados != -1){
            activitySettingsBinding.numeroLadosEt.setText(numeroLados.toString())
        }
    }

    private fun salvaConfiguracoes(numeroDados: Int, numeroLados: Int) {
        val editorSharedPreferences = configuracoesSharedPreferences.edit()
        editorSharedPreferences.putInt(NUMERO_DADOS_ATRIBUTO, numeroDados)
        editorSharedPreferences.putInt(NUMERO_LADOS_ATRIBUTO, numeroLados)
        editorSharedPreferences.commit()
    }
}