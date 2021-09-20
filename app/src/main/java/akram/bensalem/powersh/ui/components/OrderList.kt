package akram.bensalem.powersh.ui.components

import akram.bensalem.powersh.LocalStrings
import akram.bensalem.powersh.data.model.OrderItem
import akram.bensalem.powersh.data.model.OrderStatus
import akram.bensalem.powersh.lyricist
import akram.bensalem.powersh.ui.components.checkout.factureText
import akram.bensalem.powersh.ui.theme.*
import akram.bensalem.powersh.utils.Constants
import akram.bensalem.powersh.utils.localization.Locales
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Print
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.annotation.ExperimentalCoilApi


@ExperimentalAnimationApi
@ExperimentalMaterialApi
@OptIn(ExperimentalCoilApi::class)
@Composable
fun orderItemEntry(
    modifier: Modifier = Modifier,
    order: OrderItem,
    onClickEntry: () -> Unit = {},
    onPrint: () -> Unit = {},
) {

    val animatedProgress = remember {
        Animatable(initialValue = 1.15f)
    }
    LaunchedEffect(key1 = Unit) {
        animatedProgress.animateTo(
            targetValue = 1f,
            animationSpec = tween(300, easing = FastOutSlowInEasing)
        )
    }

    val animatedModifier = modifier
        .graphicsLayer(
            scaleX = animatedProgress.value,
            scaleY = animatedProgress.value
        )


    val visible = remember {
        mutableStateOf(false)
    }


    val view = LocalView.current
    val context = LocalContext.current


    Card(
        shape = Shapes.large,
        backgroundColor = MaterialTheme.colors.surface,
        elevation = Dimens.ElevationPadding.size,
        modifier = animatedModifier
            .fillMaxWidth()
            .clip(Shapes.large),
        onClick = {
            onClickEntry()
            visible.value = !visible.value
        }
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = Dimens.ZeroPadding.size, horizontal = Dimens.SmallPadding.size)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Column(
                    verticalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .padding(
                            top = Dimens.MediumPadding.size,
                            bottom = Dimens.SmallPadding.size,
                            end = Dimens.ElevationPadding.size,
                            start = Dimens.MediumPadding.size,
                        )
                ){

                    titleText(title =LocalStrings.current.id )
                    titleText(title =LocalStrings.current.date )
                    titleText(title =LocalStrings.current.total )
                    titleText(title =LocalStrings.current.status )
                }


                Column(
                    verticalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .weight(1f)
                        .padding(
                            top = Dimens.MediumPadding.size,
                            bottom = Dimens.SmallPadding.size,
                            end = Dimens.MediumPadding.size,
                            start = Dimens.ElevationPadding.size,
                        )
                ){

                    valueText(detail = "22154854" )
                    valueText(detail =order.date )
                    valueText(detail =LocalStrings.current.totalPriceValue(order.total) )
                    valueText(
                        detail =LocalStrings.current.statusValue(order.status),
                        detailColor = when(order.status){
                            OrderStatus.PENDING -> Amber500
                            OrderStatus.ACCEPTED -> PowerSHGreen
                            OrderStatus.REJECTED -> PowerSHRed
                            OrderStatus.DELIVERED -> PowerSHBlue
                            else -> MaterialTheme.colors.onSurface
                        }
                    )
                }

                IconButton(
                    onClick = {
                        onPrint()
                    },
                    modifier = Modifier
                        .background(shape = CircleShape, color = Color.Transparent)
                        .align(Alignment.Top)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Print,
                        contentDescription = LocalStrings.current.info,
                        tint = MaterialTheme.colors.primary,
                        modifier = Modifier
                            .size(24.dp)
                            .align(Alignment.CenterVertically)
                            .graphicsLayer {
                                rotationY = if (lyricist.languageTag == Locales.AR) 180f else 0f
                            },
                    )
                }



            }


            AnimatedVisibility(visible = visible.value) {
                Column(
                    verticalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            top = Dimens.SmallPadding.size,
                            bottom = Dimens.MediumPadding.size,
                            end = Dimens.MediumPadding.size,
                            start = Dimens.MediumPadding.size,
                        )
                ) {
                    Divider(color = MaterialTheme.colors.onSurface, thickness = 1.dp)

                    factureText(
                        title = LocalStrings.current.payment,
                        detail = order.payment,
                        titleColor = MaterialTheme.colors.onBackground,
                        detailColor = MaterialTheme.colors.onSurface,
                    )

                    factureText(
                        title = LocalStrings.current.adress,
                        detail = order.Address,
                        titleColor = MaterialTheme.colors.onBackground,
                        detailColor = MaterialTheme.colors.onSurface,
                    )


                    Text(
                        text = LocalStrings.current.product,
                        color = MaterialTheme.colors.onBackground,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        textAlign = TextAlign.Start,
                        modifier = Modifier
                            .padding(top = 4.dp, bottom = 8.dp)
                    )


                    order.productList.forEach { row ->
                        finalCartItem(product = row)
                        Spacer(modifier = Modifier.padding(Dimens.SmallPadding.size))
                    }
                }
            }


        }


    }

}




@Composable
fun titleText(
    title : String,
){
    Text(
        text = title,
        color = MaterialTheme.colors.onBackground,
        fontSize = 16.sp,
        fontWeight = FontWeight.Medium,
        textAlign = TextAlign.Start,
        modifier = Modifier
            .padding(top = 4.dp, bottom = 2.dp)
    )
}

@Composable
fun valueText(
    detail : String,
    detailColor: Color = MaterialTheme.colors.onSurface,
    ){
    Text(
        text = detail,
        color = detailColor,
        fontSize = 16.sp,
        fontWeight = FontWeight.Normal,
        textAlign = TextAlign.Center,
        modifier = Modifier
            .padding(start = 4.dp, top = 4.dp, bottom = 2.dp)
    )
}

/*fun createAndSaveOrderPdf(
    context: Context,
    pageHeight: Int = 1120,
    pagewidth: Int = 792
) {


    val bmp = BitmapFactory.decodeResource(
        context.resources,
        akram.bensalem.powersh.R.drawable.big_circle_powersh
    )

    val filename = "${System.currentTimeMillis()}GFG.pdf"

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {

        context.contentResolver?.also { resolver ->

            val contentValues = ContentValues().apply {

                put(MediaStore.MediaColumns.DISPLAY_NAME, filename)
                put(MediaStore.MediaColumns.MIME_TYPE, "application/pdf")
                put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOCUMENTS)
            }


            val pdfDocumentUri: Uri? =
                resolver.insert(MediaStore.Files.getContentUri("external"), contentValues)

            val pdfDocument =PdfDocument(PdfWriter(pdfDocumentUri?.let {
                resolver.openOutputStream(it)
            }))

            val document = Document(pdfDocument)

            val text = Paragraph("My Text")
            document.add(text)

            val boldText = Paragraph("My Styled Text")
            boldText.setBold()
            document.add(boldText)

            val sizedText = Paragraph("My Sized Text")
            sizedText.setFontSize(20.0f)
            document.add(sizedText)

            val coloredText = Paragraph("My Sized Text")
            coloredText.setFontColor(ColorConstants.RED)
            document.add(coloredText)

            val alignedText = Paragraph("My Sized Text")
            alignedText.setTextAlignment(TextAlignment.CENTER)
            document.add(alignedText)

            document.close()



        }


    } else {
        val fileDir =
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
        Log.d("AkramTestImage", "imagesDir is : ${fileDir} }")

        val outputStream = File(fileDir, filename)


        val pdfDocument =PdfDocument(PdfWriter(outputStream))

        val document = Document(pdfDocument)

        val text = Paragraph("My Text")
        document.add(text)

        val boldText = Paragraph("My Styled Text")
        boldText.setBold()
        document.add(boldText)

        val sizedText = Paragraph("My Sized Text")
        sizedText.setFontSize(20.0f)
        document.add(sizedText)

        val coloredText = Paragraph("My Sized Text")
        coloredText.setFontColor(ColorConstants.RED)
        document.add(coloredText)

        val alignedText = Paragraph("My Sized Text")
        alignedText.setTextAlignment(TextAlignment.CENTER)
        document.add(alignedText)

        document.close()
    }


}*/


@ExperimentalAnimationApi
@OptIn(ExperimentalMaterialApi::class)
@Preview
@Composable
fun orderItemEntryPreview() {
    PowerSHTheme {
        orderItemEntry(
            modifier = Modifier,
            order = OrderItem(
                productList = Constants.cartList1,
                date = ""
            ),
        )
    }
}