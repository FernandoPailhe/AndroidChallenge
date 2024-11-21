package com.ferpa.androidchallenge.presentation.common

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Icon
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Text
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.TextField
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.ferpa.androidchallenge.R

@Composable
fun SearchBar(
    query: String,
    onQueryChanged: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = stringResource(id = R.string.search_placeholder)
) {
    TextField(
        value = query,
        onValueChange = onQueryChanged,
        modifier = modifier
            .fillMaxWidth(),
        placeholder = { Text(text = placeholder) },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search Icon",
                tint = MaterialTheme.colorScheme.inverseOnSurface.copy(alpha = 0.8f)
            )
        },
        singleLine = true,
        shape = RoundedCornerShape(8.dp),
        colors = TextFieldDefaults.textFieldColors(
            textColor = MaterialTheme.colorScheme.inverseOnSurface, // White text color
            disabledTextColor = Color.Transparent,
            backgroundColor = Color.Transparent,
            cursorColor = MaterialTheme.colorScheme.inverseOnSurface, // White cursor
            errorCursorColor = Color.Red, // Red cursor for errors
            focusedIndicatorColor = Color.Transparent, // White border when focused
            unfocusedIndicatorColor = Color.Transparent, // Slightly transparent white border
            disabledIndicatorColor = Color.Transparent, // No border when disabled
            placeholderColor = MaterialTheme.colorScheme.inverseOnSurface.copy(alpha = 0.6f), // Placeholder color
            leadingIconColor = MaterialTheme.colorScheme.inverseOnSurface, // Icon color when active
            unfocusedLabelColor = MaterialTheme.colorScheme.inverseOnSurface.copy(alpha = 0.6f), // Label color
            focusedLabelColor = MaterialTheme.colorScheme.inverseOnSurface // Label color when focused
        )
    )

}