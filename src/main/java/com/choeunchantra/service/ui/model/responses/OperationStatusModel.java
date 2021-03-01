package com.choeunchantra.service.ui.model.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OperationStatusModel {
	private String operationResult;
	private String operationName;
}
