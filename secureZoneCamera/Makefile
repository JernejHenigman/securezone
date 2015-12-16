AXIS_USABLE_LIBS = UCLIBC GLIBC
include $(AXIS_TOP_DIR)/tools/build/rules/common.mak

PROG	= secureZoneCamera

CFLAGS += -Wall -g -O2
ifeq ($(AXIS_BUILDTYPE),host)
LDFLAGS += -lcapturehost -ljpeg
else
LDFLAGS += -lcapture
endif # AXIS_BUILDTYPE == host


PKGS = gio-2.0 glib-2.0 axevent
CFLAGS += $(shell PKG_CONFIG_PATH=$(PKG_CONFIG_LIBDIR) pkg-config --cflags $(PKGS))
LDLIBS += $(shell PKG_CONFIG_PATH=$(PKG_CONFIG_LIBDIR) pkg-config --libs $(PKGS))

CFLAGS   += -W \
	    -Wformat=2 \
	    -Wpointer-arith \
	    -Wbad-function-cast \
	    -Wstrict-prototypes \
	    -Wmissing-prototypes \
	    -Winline \
	    -Wdisabled-optimization \
	    -Wfloat-equal \
	    -Wall 

all:	$(PROG)

clean:
	rm -f $(PROG) *.o core
