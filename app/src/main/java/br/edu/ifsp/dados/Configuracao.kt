package br.edu.ifsp.dados

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Configuracao(val numeroDados: Int = 1, val numeroLados: Int = 6): Parcelable {
}