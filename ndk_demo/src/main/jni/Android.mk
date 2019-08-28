LOCAL_PATH := $(call my-dir)
include $(CLEAR_VARS)

LOCAL_LDLIBS    := -lm -llog

LOCAL_MODULE := ArraySum
LOCAL_SRC_FILES := array.c
include $(BUILD_SHARED_LIBRARY)