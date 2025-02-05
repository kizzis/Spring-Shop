package com.sparta.myselectshop.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.sparta.myselectshop.dto.FolderResponseDto;
import com.sparta.myselectshop.entity.Folder;
import com.sparta.myselectshop.entity.User;
import com.sparta.myselectshop.repository.FolderRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FolderService {
	private final FolderRepository folderRepository;

	// 로그인한 회원에 폴더들 등록
	public void addFolders(List<String> folderNames, User user) {
		// 입력으로 들어온 폴더 이름을 기준으로, 회원이 이미 생성한 폴더들을 조회합니다.
		List<Folder> existFolders = folderRepository.findAllByUserAndNameIn(user, folderNames);

		List<Folder> newFolders = new ArrayList<>();

		// 이미 생성한 폴더가 아닌 경우만 폴더 생성
		for (String folderName : folderNames) {
			if (!isExistFolderName(folderName, existFolders)) {
				Folder folder = new Folder(folderName, user);
				newFolders.add(folder);
			} else {
				throw new IllegalArgumentException("폴더명이 중복되었습니다.");
			}
		}
		folderRepository.saveAll(newFolders);
	}

	private boolean isExistFolderName(String folderName, List<Folder> existFolders) {
		// 기존 폴더 리스트에서 folder name 이 있는지?
		for (Folder existFolder : existFolders) {
			if (folderName.equals(existFolder.getName())) {
				return true;
			}
		}
		return false;
	}

	// 로그인한 회원이 등록된 모든 폴더 조회
	public List<FolderResponseDto> getFolders(User user) {
		List<Folder> folders = folderRepository.findAllByUser(user);
		List<FolderResponseDto> folderResponseDtos = new ArrayList<>();

		for (Folder folder : folders) {
			folderResponseDtos.add(new FolderResponseDto(folder));
		}

		return folderResponseDtos;
	}
}
