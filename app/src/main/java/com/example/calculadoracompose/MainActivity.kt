package com.example.calculadoracompose

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow

import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.calculadoracompose.ui.theme.CalculadoraComposeTheme





/*
* --------------------------------------------------------------------------
* Ojo: El programa valida en base a inputText [225-314]
*  inputText = "15x12" -> si encuentra un operador "x" bloquea los operadores toma el primer valor y segundo valor
*  inputText = "15-12" -> si encuentra un operador "-" bloquea los operadores
*  inputText = "-15-12" -> En este caso este el el blug de la calculadora
* --------------------------------------------------------------------------
*
*
* ---------------------RETO DE MEJORA DE LA APP---------------
* -La calculadora debe validar en base a sus valores haciendo uso de expresiones
* regulares para acortar el codigo y asi darle validacion de numeros negativos de forma independiente
* para los:
*   -var val1 = "";
*   -var val2 = "";
*
* -Haciendo uso del split y (regex)
* inputText.substring(0, val1.length) -> Primer valor, para validar el negaitvo   String.toDouble()
* inputText.substring(val1.length, val1.length+1) -> Operacion
* inputText.substring(val1.length+1, val1.length+val2.length /* o inputText.length*/) -> Segundo valor, para validar el negaitivo, String.toDouble()
*
* -Al borrar uno por uno, tenemos que validar a nivel de valores una ves hecho eso, activamos el estado
* para poder visualizar en el screen (val1, val2)
*
* - Crear una clase y envolver el codigo [66-83], singleton
*
* - POO
*
*--------------------- BUG  :( ---------------
*
* La calculadora no trabaja con valores negativos, esto es por la validacion   [225-314]
*
* */


// Integrar en un objeto y poner estas constantes en de tipo static
const val val_multiplication:  Char = 'x';
const val val_division: Char = '/';
const val val_subtraction: Char = '-';
const val val_sum: Char = '+';
const val val_equal: Char = '=';
const val val_point: Char = '.';

var numbersCalculator = listOf<List<Char>>(
    listOf<Char>('1',         '2',    '3',          val_multiplication),
    listOf<Char>('4',         '5',    '6',          val_division),
    listOf<Char>('7',         '8',    '9',          val_subtraction),
    listOf<Char>(val_point,   '0',    val_equal,    val_sum),

    /* Convertir objetos
    * {value='1', color:Color.White}
    * {value='x', color:Color.Orange}
    * */
)

var resultText = ""
var inputText = ""
var val1 = "";
var val2 = "";


//Logica de la calculadora

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            CalculadoraComposeTheme {
                Surface(color = MaterialTheme.colors.background) {
                    BuildCalculator(this.resources.configuration.screenWidthDp)
                }
            }
        }
    }
}


@Composable
private fun BuildCalculator(widthDivice: Int){
    Column (modifier = Modifier.padding(all = 4.dp)) {

        /*Manejadores de estados para mostrar en la pantalla*/
        var inputValue by rememberSaveable {
            mutableStateOf(inputText)
        }
        var resultValue by rememberSaveable {
            mutableStateOf(resultText)
        }

        /* Resultados text */
        Column (modifier = Modifier
            .padding(all = 4.dp)
            .padding(bottom = 10.dp)
            .background(color = MaterialTheme.colors.primary)) {
            Card (modifier = Modifier.padding(5.dp),backgroundColor = MaterialTheme.colors.primary , border = BorderStroke(2.dp,Color.White)) {
                Column(modifier = Modifier.padding(all = 4.dp)) {
                    Text(
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        text = resultValue, textAlign = TextAlign.End, fontWeight = FontWeight.Bold, fontSize = 45.sp, modifier = Modifier.fillMaxWidth(), color = Color.White
                    )
                    Text(
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        text = inputValue, textAlign = TextAlign.End, fontWeight = FontWeight.Bold, fontSize = 30.sp, modifier = Modifier.fillMaxWidth(), color = Color.White
                    )
                }
            }
        }

        //Botones de la calculadora
        EditNumbers(widthDevice = widthDivice, updateValues = {
            inputValue = inputText
            resultValue = resultText
        })

        NumbersButtons(widthDivice, updateValues = {
            inputValue = inputText
            resultValue = resultText
        })


    }
}

@Composable
private fun EditNumbers(widthDevice: Int, updateValues:()->Unit){
    val ctx = LocalContext.current

    Row() {
        Button(
            modifier = Modifier
                .width((widthDevice / 2).dp)
                .height(90.dp)
                .padding(all = 4.dp),
            onClick = {
                inputText = ""
                val1 = ""
                val2 = ""
                resultText =""
                updateValues()
            },
        ) {
            Text("CA", textAlign = TextAlign.Center,  fontWeight = FontWeight.Bold, fontSize = 30.sp)
        }

        Button(
            modifier = Modifier
                .width((widthDevice / 2).dp)
                .height(90.dp)
                .padding(all = 4.dp),
            onClick = {
                Toast.makeText(ctx, "Tienes que programar esta opcion", Toast.LENGTH_SHORT).show()

//                A nivel del texto, falta a nivel de valor de entrada
//                if (inputText.length==1) inputText="0"
//                else if (inputText.length > 1) inputText = inputText.substring(startIndex = 0, endIndex = (inputText.length-1))

                //Usar logica para poder borrar los datos y poder acomodar principalmente (val1, val2) icluyendo el texto de salida
                updateValues()
            },
        ) {
            Text("C", textAlign = TextAlign.Center,  fontWeight = FontWeight.Bold, fontSize = 30.sp)
        }
    }
}



@Composable
fun NumbersButtons (widthDevice: Int, updateValues:()->Unit) {
    var widthButton = (widthDevice/numbersCalculator[0].size).toInt(); //Para poder hacer adaptatito el tamaño de los botones
    Column( modifier = Modifier
        .width((widthDevice).dp)
        .background(color = Color.White) ) {
        for (row in numbersCalculator){
            Row() {
                for (valueRow in row){
                    ButtonNumberCustom(value = valueRow, width = widthButton, updateValues = updateValues)
                }
            }
        }
    }
}

@Composable
private fun ButtonNumberCustom(value: Char, width: Int, updateValues:()->Unit){
    val ctx = LocalContext.current
    Button(
        modifier = Modifier
            .width(width.dp)
            .height(100.dp)
            .padding(all = 4.dp),
        onClick = {
            var indexOp = inputText.indexOfAny(chars = listOf<Char>(val_multiplication, val_division, val_sum, val_subtraction).toCharArray())
            var indexLasOp = inputText.lastIndexOfAny(chars = listOf<Char>(val_multiplication, val_division, val_sum, val_subtraction).toCharArray())

            // Refactor con objetos y funciones
            if (value!=val_equal ) {

                var operationInserted = "$val_multiplication$val_division$val_sum$val_subtraction".contains(value)

                if (
                    (inputText.contains(val_multiplication)
                    || inputText.contains(val_division)
                    || inputText.contains(val_sum)
                    || inputText.contains(val_subtraction)
                    )
                ){/* Si se selecciono el sijno agarra el segundo valor */

                    if(operationInserted && indexLasOp==inputText.length-1){
                        inputText = inputText.substring(startIndex = 0, endIndex = inputText.length-1)
                        inputText += value
                    }

                    var noRepitPoint = (val2+value).indexOf('.') == (val2+value).lastIndexOf('.')
                    if (!operationInserted && noRepitPoint){/*Validacion de operador aritmetico*/
                        val2 += value; /*Guarda el segundo numero 256, listo para usarse */
                        inputText += value;/* No guarda el operador, lo evita para que se vea algo asi 152x256*/
                    }

                } else {/*Agarra el primer valor y al captar  el tipo de operacion manda al sgundo valor*/

                    /*var matched: Boolean =false;
                    if((val1 + value).isNotEmpty()){
                        val regex = Regex(pattern = "^\\d{0,9}(\\.\\d{0,9})?\$")
                        matched = regex.containsMatchIn(input = (val1 + value))
                    }*/


                    var noRepitPoint = ( (val1+value).indexOf('.') == (val1+value).lastIndexOf('.') )

                    /*Validacion de operador aritmetico*/
                    if (!operationInserted && noRepitPoint)val1 += value; // Solo guarda el dato numerico sin operadores, esto se usara para la operacion 152 */

                    /*Validacion del . poner por defecto .0 */
                    if (operationInserted && inputText==".") {
                        inputText = ".0"
                        val1 = ".0"
                    }
                    /*Validacion del empty valor poder defecto el 0 */
                    if (inputText=="" && operationInserted) {
                        inputText="0"
                        val1 = "0"
                    }


                    if (noRepitPoint) inputText += value; /* Guarda el primer operador para que se vea bonito en la vista 152x*/
                }


            } else if (value==val_equal && val1.isNotBlank() && val2.isNotBlank()){

                if (resultText!=""){
                    Toast.makeText(ctx, "Cargando xD", Toast.LENGTH_SHORT).show()

                    /*Limpiando datos con el primer valor*/
                    inputText=resultText
                    val1=resultText

                    resultText=""
                    val2=""

                } else {

                    if (val2 == ".") {
                        val2 += "0";
                        inputText += "0"
                    }
                    //Calcular el resultado

                    when(inputText[indexOp]){
                        val_multiplication  -> resultText = (val1.toDouble() * val2.toDouble()).toString()
                        val_division        -> resultText = (val1.toDouble() / val2.toDouble()).toString()
                        val_sum             -> resultText = (val1.toDouble() + val2.toDouble()).toString()
                        val_subtraction     -> resultText = (val1.toDouble() - val2.toDouble()).toString()
                    }

                    var strNum = resultText.substring(startIndex = resultText.indexOf(".")+1, endIndex = resultText.length)
                    if (strNum.length==1 && strNum=="0") resultText = resultText.substring(startIndex = 0, endIndex = resultText.indexOf("."))

                }
            }

//            Toast.makeText(ctx, "$val1 - $val2", Toast.LENGTH_SHORT).show()
            updateValues()
        },
    ) {
        Text(text = "$value", textAlign = TextAlign.Center,  fontWeight = FontWeight.Bold, fontSize = 30.sp)
    }
}



@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    CalculadoraComposeTheme {

    }
}