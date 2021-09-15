package com.it_academy.data.flow_transformer

interface Transformer<InputType, OutputType> {
    var convert : (InputType) -> OutputType
}