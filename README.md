# CalculadoraCompose

## Ojo: El programa valida en base a inputText [225-314]
* --------------------------------------------------------------------------
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
