package com.finalproject.priotask.common

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.finalproject.priotask.ui.theme.PrioTaskTheme
import com.finalproject.priotask.util.PlaceholderTransformation

@Composable
fun FormTextField(
    value: String = "",
    placeHolder: String = "",
    onValueChange: (String) -> Unit = {},
    keyboardOptions: KeyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
    visualTransformation: VisualTransformation = VisualTransformation.None
) {
    TextField(
        keyboardOptions = keyboardOptions,
        value = value,
        onValueChange = onValueChange,
        placeholder = {
            Text(text = placeHolder)
        },
        shape = RoundedCornerShape(48.dp),
        colors = TextFieldDefaults.textFieldColors(
            textColor = Color.Gray,
            disabledTextColor = Color.Transparent,
            backgroundColor = Color.White,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        ),
        visualTransformation = visualTransformation,
        modifier = Modifier
            .fillMaxWidth()
            .shadow(
                elevation = 12.dp,
                shape = RoundedCornerShape(48.dp)
            )
            .drawWithContent {
                val paddingPx = 8.dp.toPx()
                clipRect(
                    left = 0f,
                    top = 0f,
                    right = size.width,
                    bottom = size.height + paddingPx
                ) {
                    this@drawWithContent.drawContent()
                }
            }
    )
}

@Composable
fun LabeledTextFieldInput(
    value: String = "",
    placeHolder: String = "",
    onValueChange: (String) -> Unit = {},
    keyboardOptions: KeyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
    visualTransformation: VisualTransformation = VisualTransformation.None
) {
    Surface(
        modifier = Modifier,
        elevation = 4.dp,
        shape = RoundedCornerShape(24.dp)
    ) {
        TextField(
            keyboardOptions = keyboardOptions,
            value = value,
            onValueChange = onValueChange,
            label = {
                Text(text = placeHolder)
            },
//            shape = RoundedCornerShape(48.dp),
            colors = TextFieldDefaults.textFieldColors(
                textColor = Color.Gray,
                disabledTextColor = Color.Transparent,
                backgroundColor = Color.White,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            ),
//            minLines = 3,
//            textStyle = TextStyle(
//                lineHeight = 3.em,
//                lineHeightStyle = LineHeightStyle(
//                    LineHeightStyle.Alignment.Center,
//                    LineHeightStyle.Trim.None
//                )
//            ),
            visualTransformation = visualTransformation,
            modifier = Modifier
                .fillMaxWidth()
//                .padding(vertical = 6.dp)
//                .height(100.dp)
//                .align(Alignment.CenterStart)
            //            .height(80.dp)
//                        .shadow(
//                            elevation = 12.dp,
//                            shape = RoundedCornerShape(48.dp)
//                        )
            //            .drawWithContent {
            //                val paddingPx = 8.dp.toPx()
            //                clipRect(
            //                    left = 0f,
            //                    top = 0f,
            //                    right = size.width,
            //                    bottom = size.height + paddingPx
            //                ) {
            //                    this@drawWithContent.drawContent()
            //                }
            //            }
        )
    }
}

@Composable
fun LabeledTextField(
    modifier: Modifier = Modifier,
    label: String,
    text: String = ""
) {
    Surface(
        modifier = modifier,
        elevation = 4.dp,
        shape = RoundedCornerShape(24.dp),
    ) {
        TextField(
            readOnly = true,
            value = "",
            onValueChange = {},
            label = { Text(text = label) },
            colors = TextFieldDefaults.textFieldColors(
                textColor = Color.Gray,
                disabledTextColor = Color.Transparent,
                backgroundColor = Color.White,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            ),
            visualTransformation = PlaceholderTransformation(text)
        )
    }
}

@Composable
fun DropDownWithTextField(
    modifier: Modifier = Modifier,
    value: String = "",
    onValueChange: (String) -> Unit = {},
    keyboardOptions: KeyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
    visualTransformation: VisualTransformation = VisualTransformation.None
) {
    Surface(
        modifier = modifier,
        elevation = 4.dp,
        shape = RoundedCornerShape(24.dp)
    ) {
        TextField(
            keyboardOptions = keyboardOptions,
            value = value,
            onValueChange = onValueChange,
            colors = TextFieldDefaults.textFieldColors(
                textColor = Color.Gray,
                disabledTextColor = Color.Transparent,
                backgroundColor = Color.White,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            ),
            visualTransformation = visualTransformation,
            readOnly = true,
            modifier = Modifier
                .fillMaxWidth()
        )
    }
}

//@Preview(showBackground = true)
//@Composable
//fun LoginTextFieldPreview() {
//    PrioTaskTheme {
//        Column(Modifier.padding(12.dp)) {
//            FormTextField(placeHolder = "Username")
//            Spacer(modifier = Modifier.height(32.dp))
//            FormTextField(placeHolder = "Password")
//        }
//    }
//}

@Preview(showBackground = true)
@Composable
fun LabeledTextFieldPreview() {
    PrioTaskTheme {
        Column(Modifier.padding(12.dp)) {
            LabeledTextFieldInput(placeHolder = "Username")
            Spacer(modifier = Modifier.height(32.dp))
            LabeledTextFieldInput(placeHolder = "Password")
        }
    }
}

@Composable
fun ImagePainter(
    @DrawableRes id: Int
) {
    Image(
        painter = painterResource(id),
        contentDescription = "",
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .fillMaxSize()
            .padding(48.dp)
    )
}