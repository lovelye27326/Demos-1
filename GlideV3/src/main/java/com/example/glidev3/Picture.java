package com.example.glidev3;

import com.example.glidev3.glide.IPicture;

public class Picture implements IPicture {

  public Picture() {

  }

  public Picture(String fileName) {
    this.fileName = fileName;
  }

  private String fileName;

  public String getFileName() {
    return fileName;
  }

  public void setFileName(String fileName) {
    this.fileName = fileName;
  }
}
