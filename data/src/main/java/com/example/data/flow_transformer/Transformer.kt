package com.example.data.flow_transformer

interface Transformer<InputType, OutputType> {
    var convert : (InputType) -> OutputType
}