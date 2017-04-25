package com.example.woutervannuland.hue.bridgelist;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.woutervannuland.hue.R;
import com.philips.lighting.hue.sdk.PHAccessPoint;

import nl.rwslinkman.presentable.Presenter;

public class AccessPointPresenter implements Presenter<PHAccessPoint, AccessPointPresenter.ViewHolder> {
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.listitem_accesspoint, parent, false);

        ViewHolder holder = new ViewHolder(v);
        holder.ipAddressView = (TextView) v.findViewById(R.id.item_ipaddress);

        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, PHAccessPoint item)
    {
        String ipAddress = item.getIpAddress();
        viewHolder.ipAddressView.setText(ipAddress);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView ipAddressView;

        ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
