package com.example.gr1accdnbp2023b

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class ESqliteHelperEntrenador(
    contexto: Context?, // THIS
) : SQLiteOpenHelper(
    contexto,
    "moviles", //nombre BDD
    null,
    1
) {
    override fun onCreate(db: SQLiteDatabase?) {
        val scriptSQLCrearTablaEntrenador =
            """
                CREATE TABLE ENTRENADOR(
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                nombre VARCHAR(50),
                descripcion VARCHAR(50)
                )
            """.trimIndent()
        db?.execSQL(scriptSQLCrearTablaEntrenador)
    }

    override fun onUpgrade(p0: SQLiteDatabase?,
                           p1: Int,
                           p2: Int) {}
    fun crearEntrenador(
        nombre: String,
        descripcion: String
    ) : Boolean {
        val basedatosEscritura = writableDatabase
        val valoresAguardar = ContentValues()
        valoresAguardar.put("nombre", nombre)
        valoresAguardar.put("descripcion", descripcion)
        val resultadoAGuardar = basedatosEscritura
            .insert(
                "ENTRENADOR", //nombre de tabla
                null,
                valoresAguardar // valores
            )
        basedatosEscritura.close()
        return if (resultadoAGuardar.toInt() == -1) false else true
    }

    fun eliminarEntrenadorFormulario(id: Int): Boolean {
        val conexionEscritura = writableDatabase
        val parametrosConsultaDelete = arrayOf( id.toString())
        val resultadoEliminacion = conexionEscritura
            .delete(
                "ENTRENADOR", //Nombre tabla
                "id=?", //Consulta Where
                parametrosConsultaDelete
            )
        conexionEscritura.close()
        return if(resultadoEliminacion == -1) false else true
    }

    fun actualizarEntrenadorFormulario(
        nombre: String,
        descripcion: String,
        id: Int
    ) : Boolean {
        val conexionEscritura = writableDatabase
        val valoresAActualizar = ContentValues()
        valoresAActualizar.put("nombre", nombre)
        valoresAActualizar.put("descripcion", descripcion)
        val parametrosConsultaActualizar = arrayOf( id.toString() )
        val resultadoActualizacion = conexionEscritura
            .update(
                "ENTRENADOR", // Nombre tabla
                valoresAActualizar, //Valores
                "id=?", // Consulta Where
                parametrosConsultaActualizar
            )
        conexionEscritura.close()
        return if(resultadoActualizacion == -1) false else true
    }

    fun consultaEntrenadorForID(id: Int): BEntrenador {
        val baseDatosLectura = writableDatabase
        val scriptConsultaLectura = """
            SELECT * FROM ENTRENADOR WHERE ID = ?
        """.trimIndent()
        val parametrosConsultaLectura = arrayOf(id.toString())
        val resultadoConsultaLectura = baseDatosLectura.rawQuery(
            scriptConsultaLectura, //Consulta
            parametrosConsultaLectura, //Parametros
        )
        val existeUsuario = resultadoConsultaLectura.moveToFirst()
        val usuarioEncontrado = BEntrenador(0, "", "")
        // val arreglo = arrayListOf<BEntrenador>()
        if (existeUsuario){
            do {
                val id = resultadoConsultaLectura.getInt(0) //Indice 0
                val nombre = resultadoConsultaLectura.getString(1) //Indice 1
                val descripcion = resultadoConsultaLectura.getString(2)
                if(id != null){
                    //val usuarioEncontrado = BEntrenador(0, "", "")
                    usuarioEncontrado.id = id
                    usuarioEncontrado.nombre = nombre
                    usuarioEncontrado.descripcion = descripcion
                    //arreglo.add(usuarioEncontrado)
                }
            } while (resultadoConsultaLectura.moveToNext())
        }
        resultadoConsultaLectura.close()
        baseDatosLectura.close()
        return usuarioEncontrado
    }

}