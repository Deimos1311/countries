package com.example.data.flow_trasformer

interface Transformer<InputType, OutputType> {
    var convert : (InputType) -> OutputType
}